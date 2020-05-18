package io.realworld

import kotlinx.coroutines.await
import pl.treksoft.jquery.JQueryAjaxSettings
import pl.treksoft.jquery.JQueryXHR
import pl.treksoft.kvision.rest.HttpMethod
import pl.treksoft.kvision.rest.RestClient

object Api {

    const val API_URL = "https://conduit.productionready.io/api"

    private val restClient = RestClient()

    private fun authRequest(xhr: JQueryXHR, @Suppress("UNUSED_PARAMETER") settings: JQueryAjaxSettings): Boolean {
        ConduitManager.getJwtToken()?.let {
            xhr.setRequestHeader("Authorization", "Token $it")
        }
        return true
    }

    suspend fun login(email: String?, password: String?): User {
        return restClient.call<UserDto, UserDto>(
            "$API_URL/users/login",
            UserDto(User(email = email, password = password)),
            HttpMethod.POST
        ).await().user
    }

    suspend fun user(): User {
        return restClient.call<UserDto>(
            "$API_URL/user",
            beforeSend = ::authRequest
        ).await().user
    }

    suspend fun tags(): List<String> {
        return restClient.call<TagsDto>(
            "$API_URL/tags"
        ).await().tags
    }

    suspend fun articles(
        tag: String?,
        author: String?,
        favorited: String?,
        offset: Int = 0,
        limit: Int = 10
    ): ArticlesDto {
        return restClient.call<ArticlesDto, ArticlesQuery>(
            "$API_URL/articles",
            ArticlesQuery(tag, author, favorited, offset, limit),
            beforeSend = ::authRequest
        ).await()
    }

    suspend fun feed(offset: Int = 0): ArticlesDto {
        return restClient.call<ArticlesDto, FeedQuery>(
            "$API_URL/articles/feed",
            FeedQuery(offset),
            beforeSend = ::authRequest
        ).await()
    }

    suspend fun article(slug: String): Article {
        return restClient.call<ArticleDto>(
            "$API_URL/articles/$slug",
            beforeSend = ::authRequest
        ).await().article
    }

    suspend fun articleComments(slug: String): List<Comment> {
        return restClient.call<CommentsDto>(
            "$API_URL/articles/$slug/comments",
            beforeSend = ::authRequest
        ).await().comments
    }

    suspend fun articleFavorite(slug: String, favorite: Boolean = true): Article {
        return restClient.call<ArticleDto>(
            "$API_URL/articles/$slug/favorite",
            method = if (favorite) HttpMethod.POST else HttpMethod.DELETE,
            beforeSend = ::authRequest
        ).await().article
    }

    suspend fun profile(username: String): User {
        return restClient.call<ProfileDto>(
            "$API_URL/profiles/$username",
            beforeSend = ::authRequest
        ).await().profile
    }

    suspend fun profileFollow(username: String, follow: Boolean = true): User {
        return restClient.call<ProfileDto>(
            "$API_URL/profiles/$username/follow",
            method = if (follow) HttpMethod.POST else HttpMethod.DELETE,
            beforeSend = ::authRequest
        ).await().profile
    }

    suspend fun articleComment(slug: String, comment: String?): Comment {
        return restClient.call<CommentDto, CommentDto>(
            "$API_URL/articles/$slug/comments",
            CommentDto(Comment(body = comment)),
            method = HttpMethod.POST,
            beforeSend = ::authRequest
        ).await().comment
    }

    suspend fun articleCommentDelete(slug: String, id: Int) {
        restClient.remoteCall(
            "$API_URL/articles/$slug/comments/$id",
            method = HttpMethod.DELETE,
            beforeSend = ::authRequest
        ).await()
    }


}
