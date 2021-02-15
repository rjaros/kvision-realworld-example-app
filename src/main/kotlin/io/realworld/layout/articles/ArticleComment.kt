package io.realworld.layout.articles

import io.kvision.core.Container
import io.kvision.core.onClick
import io.kvision.html.div
import io.kvision.html.i
import io.kvision.html.image
import io.kvision.html.link
import io.kvision.html.p
import io.kvision.html.span
import io.kvision.types.toStringF
import io.realworld.ConduitManager
import io.realworld.ConduitState
import io.realworld.model.Article
import io.realworld.model.Comment
import kotlin.js.Date

fun Container.articleComment(state: ConduitState, comment: Comment, article: Article) {
    div(className = "card") {
        div(className = "card-block") {
            p(comment.body, className = "card-text")
        }
        div(className = "card-footer", rich = true) {
            val imageSrc =
                comment.author?.image?.ifBlank { null } ?: "https://static.productionready.io/images/smiley-cyrus.jpg"
            link("", "#/@${comment.author?.username}", className = "comment-author") {
                image(imageSrc, className = "comment-author-img")
            }
            +" &nbsp; "
            link(comment.author?.username ?: "", "#/@${comment.author?.username}", className = "comment-author")
            val createdAtFormatted = comment.createdAt?.let { Date(it).toStringF("MMMM D, YYYY") }
            span(createdAtFormatted, className = "date-posted")
            if (state.user != null && state.user.username == comment.author?.username) {
                span(className = "mod-options") {
                    i(className = "ion-trash-a").onClick {
                        ConduitManager.articleCommentDelete(article.slug!!, comment.id!!)
                    }
                }
            }
        }
    }
}
