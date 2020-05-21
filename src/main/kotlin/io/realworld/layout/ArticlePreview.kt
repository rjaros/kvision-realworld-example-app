package io.realworld.layout

import io.realworld.ConduitManager
import io.realworld.model.Article
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.html.*
import pl.treksoft.kvision.types.toStringF
import kotlin.js.Date

fun Container.articlePreview(article: Article) {
    div(className = "article-preview") {
        div(className = "article-meta") {
            val image =
                article.author?.image?.ifBlank { null } ?: "https://static.productionready.io/images/smiley-cyrus.jpg"
            link("", "#/@${article.author?.username}", image = image)
            div(className = "info") {
                link(article.author?.username ?: "", "#/@${article.author?.username}", className = "author")
                val createdAtFormatted = article.createdAt?.let { Date(it).toStringF("MMMM D, YYYY") }
                span(createdAtFormatted, className = "date")
            }
            val btnStyle = if (article.favorited) ButtonStyle.PRIMARY else ButtonStyle.OUTLINEPRIMARY
            button(
                article.favoritesCount.toString(),
                "ion-heart", btnStyle,
                className = "pull-xs-right"
            ) {
                size = ButtonSize.SMALL
                onClick {
                    ConduitManager.toggleFavoriteArticle(article)
                }
            }
        }
        link("", "#/article/${article.slug}", className = "preview-link") {
            h1(article.title)
            p(article.description)
            span("Read more...")
            if (article.tagList.isNotEmpty()) {
                ul(className = "tag-list") {
                    article.tagList.forEach {
                        li(" $it ", className = "tag-default tag-pill tag-outline")
                    }
                }
            }
        }
    }
}

