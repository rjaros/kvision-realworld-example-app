package io.realworld.layout

import io.realworld.ConduitManager
import io.realworld.ConduitState
import io.realworld.View
import io.realworld.marked
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.form.form
import pl.treksoft.kvision.form.text.TextAreaInput
import pl.treksoft.kvision.form.text.textAreaInput
import pl.treksoft.kvision.html.*
import pl.treksoft.kvision.utils.obj

fun Container.article(state: ConduitState) {
    if (state.article != null) {
        val article = state.article
        div(className = "article-page") {
            div(className = "banner") {
                div(className = "container") {
                    h1(article.title)
                    articleMeta(article)
                }
            }
            div(className = "container page") {
                div(className = "row article-content") {
                    div(className = "col-md-12") {
                        div(marked(article.body!!, obj { sanitize = true }), rich = true)
                        if (article.tagList.isNotEmpty()) {
                            ul(className = "tag-list") {
                                article.tagList.forEach {
                                    li(it, className = "tag-default tag-pill tag-outline")
                                }
                            }
                        }
                    }
                }
                tag(TAG.HR)
                div(className = "article-actions") {
                    articleMeta(article)
                }
                div(className = "row") {
                    div(className = "col-xs-12 col-md-8 offset-md-2") {
                        if (state.user != null) {
                            form(className = "card comment-form") {
                                lateinit var commentInput: TextAreaInput
                                div(className = "card-block") {
                                    commentInput = textAreaInput(rows = 3, className = "form-control") {
                                        placeholder = "Write a comment..."
                                    }
                                }
                                div(className = "card-footer") {
                                    image(state.user.image?.ifBlank { null }, className = "comment-author-img")
                                    button("Post Comment") {
                                        size = ButtonSize.SMALL
                                        onClick {
                                            ConduitManager.articleComment(article.slug!!, commentInput.value)
                                        }
                                    }
                                }
                            }
                        } else {
                            p(
                                "<a href=\"#${View.LOGIN.url}\">Sign in</a> or <a href=\"#${View.REGISTER.url}\">sign up</a> to add comments on this article.",
                                rich = true
                            )
                        }
                        state.articleComments?.forEach {
                            articleComment(state, it, article)
                        }
                    }
                }
            }
        }
    }
}
/*
<div class="article-page">

<div class="container page">

<div class="row">

  <div class="col-xs-12 col-md-8 offset-md-2">

    <form class="card comment-form">
      <div class="card-block">
        <textarea class="form-control" placeholder="Write a comment..." rows="3"></textarea>
      </div>
      <div class="card-footer">
        <img src="http://i.imgur.com/Qr71crq.jpg" class="comment-author-img" />
        <button class="btn btn-sm btn-primary">
         Post Comment
        </button>
      </div>
    </form>

    <div class="card">
      <div class="card-block">
        <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
      </div>
      <div class="card-footer">
        <a href="" class="comment-author">
          <img src="http://i.imgur.com/Qr71crq.jpg" class="comment-author-img" />
        </a>
        &nbsp;
        <a href="" class="comment-author">Jacob Schmidt</a>
        <span class="date-posted">Dec 29th</span>
      </div>
    </div>

    <div class="card">
      <div class="card-block">
        <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
      </div>
      <div class="card-footer">
        <a href="" class="comment-author">
          <img src="http://i.imgur.com/Qr71crq.jpg" class="comment-author-img" />
        </a>
        &nbsp;
        <a href="" class="comment-author">Jacob Schmidt</a>
        <span class="date-posted">Dec 29th</span>
        <span class="mod-options">
          <i class="ion-edit"></i>
          <i class="ion-trash-a"></i>
        </span>
      </div>
    </div>

  </div>

</div>

</div>

</div>
 */
