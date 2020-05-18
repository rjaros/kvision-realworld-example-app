package io.realworld.layout

import io.realworld.Article
import io.realworld.ConduitManager
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.html.ButtonSize
import pl.treksoft.kvision.html.ButtonStyle
import pl.treksoft.kvision.html.button
import pl.treksoft.kvision.html.div
import pl.treksoft.kvision.html.link
import pl.treksoft.kvision.html.span
import pl.treksoft.kvision.types.toStringF
import pl.treksoft.kvision.utils.px
import kotlin.js.Date

fun Container.articleMeta(article: Article) {
    div(className = "article-meta") {
        val image =
            article.author?.image?.ifBlank { null } ?: "https://static.productionready.io/images/smiley-cyrus.jpg"
        link("", "#/@${article.author?.username}", image = image)
        div(className = "info") {
            link(article.author?.username ?: "", "#/@${article.author?.username}", className = "author")
            val createdAtFormatted = article.createdAt?.let { Date(it).toStringF("MMMM D, YYYY") }
            span(createdAtFormatted, className = "date")
        }
        if (article.author?.following == true) {
            button(
                "Unfollow ${article.author.username}",
                "ion-plus-round",
                ButtonStyle.SECONDARY,
                separator = "&nbsp; "
            ) {
                size = ButtonSize.SMALL
                onClick {
                    ConduitManager.toggleProfileFollow(article.author)
                }
            }
        } else {
            button(
                "Follow ${article.author?.username}",
                "ion-plus-round",
                ButtonStyle.OUTLINESECONDARY,
                separator = "&nbsp; "
            ) {
                size = ButtonSize.SMALL
                onClick {
                    ConduitManager.toggleProfileFollow(article.author!!)
                }
            }
        }
        if (article.favorited) {
            button("Unfavorite Post ", "ion-heart", separator = "&nbsp; ") {
                marginLeft = 5.px
                size = ButtonSize.SMALL
                span("(${article.favoritesCount})", className = "counter")
                onClick {
                    ConduitManager.toggleFavoriteArticle(article)
                }
            }
        } else {
            button("Favorite Post ", "ion-heart", ButtonStyle.OUTLINEPRIMARY, separator = "&nbsp; ") {
                marginLeft = 5.px
                size = ButtonSize.SMALL
                span("(${article.favoritesCount})", className = "counter")
                onClick {
                    ConduitManager.toggleFavoriteArticle(article)
                }
            }
        }
    }
}
