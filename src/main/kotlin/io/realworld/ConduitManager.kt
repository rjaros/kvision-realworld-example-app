package io.realworld

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import org.w3c.dom.get
import org.w3c.dom.set
import pl.treksoft.kvision.redux.createReduxStore
import pl.treksoft.navigo.Navigo
import kotlin.browser.localStorage

object ConduitManager : CoroutineScope by CoroutineScope(Dispatchers.Default + SupervisorJob()) {

    const val JWT_TOKEN = "jwtToken"

    private val routing = Navigo(null, true, "#")
    val conduitStore = createReduxStore(::conduitReducer, ConduitState())

    private fun afterInitialize(feedType: FeedType) {
        conduitStore.dispatch(ConduitAction.AppLoaded)
        if (conduitStore.getState().view == View.HOME) {
            selectFeed(feedType)
            loadTags()
        }
    }

    fun initialize() {
        routing.initialize().resolve()
        if (getJwtToken() != null) {
            withProgress {
                try {
                    val user = Api.user()
                    conduitStore.dispatch(ConduitAction.Login(user))
                    saveJwtToken(user.token!!)
                    afterInitialize(FeedType.USER)
                } catch (e: Exception) {
                    console.log("Invalid JWT Token")
                    deleteJwtToken()
                    afterInitialize(FeedType.GLOBAL)
                }
            }
        } else {
            afterInitialize(FeedType.GLOBAL)
        }
    }

    fun loadTags() {
        conduitStore.dispatch(ConduitAction.TagsLoading)
        withProgress {
            val tags = Api.tags()
            conduitStore.dispatch(ConduitAction.TagsLoaded(tags))
        }
    }

    fun loadArticles() {
        conduitStore.dispatch(ConduitAction.ArticlesLoading)
        withProgress {
            val state = conduitStore.getState()
            val offset = state.selectedPage * 10
            val articleDto = when (state.feedType) {
                FeedType.USER -> Api.feed(offset)
                FeedType.GLOBAL -> Api.articles(null, null, null, offset, 10)
                FeedType.TAG -> Api.articles(state.selectedTag, null, null, offset, 10)
                FeedType.PROFILE -> Api.articles(null, state.profile?.username, null, offset, 5)
                FeedType.PROFILE_FAVORITED -> Api.articles(null, null, state.profile?.username, offset, 5)
            }
            conduitStore.dispatch(ConduitAction.ArticlesLoaded(articleDto.articles, articleDto.articlesCount))
        }
    }

    fun homePage() {
        conduitStore.dispatch(ConduitAction.HomePage)
        val state = conduitStore.getState()
        if (!state.appLoading) {
            if (state.user != null) {
                selectFeed(FeedType.USER)
            } else {
                selectFeed(FeedType.GLOBAL)
            }
            loadTags()
        }
    }

    fun selectFeed(
        feedType: FeedType,
        selectedTag: String? = null,
        profile: User? = null
    ) {
        conduitStore.dispatch(ConduitAction.SelectFeed(feedType, selectedTag, profile))
        loadArticles()
    }

    fun selectPage(page: Int) {
        conduitStore.dispatch(ConduitAction.SelectPage(page))
        loadArticles()
    }

    fun toggleFavoriteArticle(article: Article) {
        if (conduitStore.getState().user != null) {
            withProgress {
                val articleUpdated = Api.articleFavorite(article.slug!!, !article.favorited)
                conduitStore.dispatch(ConduitAction.ArticleUpdated(articleUpdated))
            }
        } else {
            routing.navigate(View.LOGIN.url)
        }
    }

    fun showArticle(slug: String) {
        withProgress {
            val article = async { Api.article(slug) }
            val articleComments = async { Api.articleComments(slug) }
            conduitStore.dispatch(ConduitAction.ShowArticle(article.await()))
            conduitStore.dispatch(ConduitAction.ShowArticleCommets(articleComments.await()))
        }
    }

    fun toggleProfileFollow(user: User) {
        if (conduitStore.getState().user != null) {
            withProgress {
                val changedUser = Api.profileFollow(user.username!!, !user.following!!)
                conduitStore.dispatch(ConduitAction.ProfileFollowChanged(changedUser))
            }
        } else {
            routing.navigate(View.LOGIN.url)
        }
    }

    fun articleComment(slug: String, comment: String?) {
        withProgress {
            val newComment = Api.articleComment(slug, comment)
            conduitStore.dispatch(ConduitAction.AddComment(newComment))
        }
    }

    fun articleCommentDelete(slug: String, id: Int) {
        withProgress {
            Api.articleCommentDelete(slug, id)
            conduitStore.dispatch(ConduitAction.DeleteComment(id))
        }
    }

    fun showProfile(username: String, favorites: Boolean) {
        val feedType = if (favorites) FeedType.PROFILE_FAVORITED else FeedType.PROFILE
        conduitStore.dispatch(ConduitAction.ProfilePage(feedType))
        withProgress {
            val user = Api.profile(username)
            selectFeed(feedType, null, user)
        }
    }

    fun loginPage() {
        conduitStore.dispatch(ConduitAction.LoginPage)
    }

    fun registerPage() {
        conduitStore.dispatch(ConduitAction.RegisterPage)
    }

    fun login(email: String?, password: String?) {
        withProgress {
            try {
                val user = Api.login(email, password)
                conduitStore.dispatch(ConduitAction.Login(user))
                saveJwtToken(user.token!!)
                routing.navigate(View.HOME.url)
            } catch (e: Exception) {
                conduitStore.dispatch(ConduitAction.LoginError)
            }
        }
    }

    fun logout() {
        deleteJwtToken()
        conduitStore.dispatch(ConduitAction.Logout)
        routing.navigate(View.HOME.url)
    }

    private fun saveJwtToken(token: String) {
        localStorage[JWT_TOKEN] = token
    }

    private fun deleteJwtToken() {
        localStorage.removeItem(JWT_TOKEN)
    }

    fun getJwtToken(): String? {
        return localStorage[JWT_TOKEN]
    }
}
