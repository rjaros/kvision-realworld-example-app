package io.realworld.model

import kotlinx.serialization.Serializable

@Serializable
data class ArticlesQuery(
    val tag: String?,
    val author: String?,
    val favorited: String?,
    val offset: Int = 0,
    val limit: Int = 10
)

@Serializable
data class FeedQuery(
    val offset: Int = 0,
    val limit: Int = 10
)

@Serializable
data class ArticleDto(val article: Article)

@Serializable
data class ArticlesDto(val articles: List<Article>, val articlesCount: Int)

@Serializable
data class Article(
    val slug: String? = null,
    val title: String? = null,
    val description: String? = null,
    val body: String? = null,
    val tagList: List<String> = listOf(),
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val favorited: Boolean = false,
    val favoritesCount: Int = 0,
    val author: User? = null
)

@Serializable
data class CommentDto(val comment: Comment)

@Serializable
data class CommentsDto(val comments: List<Comment>)

@Serializable
data class Comment(
    val id: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val body: String? = null,
    val author: User? = null
)

@Serializable
data class TagsDto(val tags: List<String>)
