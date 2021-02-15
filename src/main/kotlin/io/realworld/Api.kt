package io.realworld

import io.realworld.model.*
import kotlinx.coroutines.await
import io.kvision.jquery.JQueryAjaxSettings
import io.kvision.jquery.JQueryXHR
import io.kvision.rest.HttpMethod
import io.kvision.rest.RestClient

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
            UserDto(
                User(
                    email = email,
                    password = password
                )
            ),
            HttpMethod.POST
        ).await().user
    }

    suspend fun register(username: String?, email: String?, password: String?): User {
        return restClient.call<UserDto, UserDto>(
            "$API_URL/users",
            UserDto(
                User(
                    username = username,
                    email = email,
                    password = password
                )
            ),
            HttpMethod.POST
        ).await().user
    }

    suspend fun user(): User {
        return restClient.call<UserDto>(
            "$API_URL/user",
            beforeSend = ::authRequest
        ).await().user
    }

    suspend fun settings(image: String?, username: String?, bio: String?, email: String?, password: String?): User {
        return restClient.call<UserDto, UserDto>(
            "$API_URL/user",
            UserDto(
                User(
                    image = image,
                    username = username,
                    bio = bio,
                    email = email,
                    password = password
                )
            ),
            HttpMethod.PUT,
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

    suspend fun feed(offset: Int = 0, limit: Int = 10): ArticlesDto {
        return restClient.call<ArticlesDto, FeedQuery>(
            "$API_URL/articles/feed",
            FeedQuery(offset, limit),
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

    suspend fun createArticle(title: String?, description: String?, body: String?, tags: List<String>): Article {
        return restClient.call<ArticleDto, ArticleDto>(
            "$API_URL/articles",
            ArticleDto(
                Article(
                    title = title,
                    description = description,
                    body = body,
                    tagList = tags
                )
            ),
            method = HttpMethod.POST,
            beforeSend = ::authRequest
        ).await().article
    }

    suspend fun updateArticle(
        slug: String,
        title: String?,
        description: String?,
        body: String?,
        tags: List<String>
    ): Article {
        return restClient.call<ArticleDto, ArticleDto>(
            "$API_URL/articles/$slug",
            ArticleDto(
                Article(
                    title = title,
                    description = description,
                    body = body,
                    tagList = tags
                )
            ),
            method = HttpMethod.PUT,
            beforeSend = ::authRequest
        ).await().article
    }

    suspend fun deleteArticle(slug: String) {
        restClient.remoteCall(
            "$API_URL/articles/$slug",
            method = HttpMethod.DELETE,
            beforeSend = ::authRequest
        ).await()
    }

}
