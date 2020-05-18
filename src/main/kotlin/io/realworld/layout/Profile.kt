package io.realworld.layout

import io.realworld.ConduitManager
import io.realworld.ConduitState
import io.realworld.FeedType
import io.realworld.View
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.html.*

fun Container.profilePage(state: ConduitState) {
    val profile = state.profile
    if (profile != null) {
        div(className = "profile-page") {
            div(className = "user-info") {
                div(className = "container") {
                    div(className = "row") {
                        div(className = "col-xs-12 col-md-10 offset-md-1") {
                            val imageSrc = profile.image?.ifBlank { null }
                                ?: "https://static.productionready.io/images/smiley-cyrus.jpg"
                            image(imageSrc, className = "user-img")
                            h4(profile.username)
                            p(profile.bio)
                            if (profile.following == true) {
                                button(
                                    "Unfollow ${profile.username}",
                                    "ion-plus-round",
                                    ButtonStyle.SECONDARY,
                                    separator = "&nbsp; ",
                                    className = "action-btn"
                                ) {
                                    size = ButtonSize.SMALL
                                    onClick {
                                        ConduitManager.toggleProfileFollow(profile)
                                    }
                                }
                            } else {
                                button(
                                    "Follow ${profile.username}",
                                    "ion-plus-round",
                                    ButtonStyle.OUTLINESECONDARY,
                                    separator = "&nbsp; ",
                                    className = "action-btn"
                                ) {
                                    size = ButtonSize.SMALL
                                    onClick {
                                        ConduitManager.toggleProfileFollow(profile)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            div(className = "container") {
                div(className = "row") {
                    div(className = "col-xs-12 col-md-10 offset-md-1") {
                        div(className = "articles-toggle") {
                            ul(className = "nav nav-pills outline-active") {
                                li(className = "nav-item") {
                                    val className =
                                        if (state.feedType == FeedType.PROFILE) "nav-link active" else "nav-link"
                                    link(
                                        "My Articles",
                                        "#${View.PROFILE.url}${profile.username}",
                                        className = className
                                    )
                                }
                                li(className = "nav-item") {
                                    val className =
                                        if (state.feedType == FeedType.PROFILE_FAVORITED) "nav-link active" else "nav-link"
                                    link(
                                        "Favorited Articles",
                                        "#${View.PROFILE.url}${profile.username}/favorites",
                                        className = className
                                    )
                                }
                            }
                        }
                        if (state.articlesLoading) {
                            div("Loading articles...", className = "article-preview")
                        } else if (!state.articles.isNullOrEmpty()) {
                            state.articles.forEach {
                                articlePreview(state, it)
                            }
                            pagination(state)
                        } else {
                            div("No articles are here... yet.", className = "article-preview")
                        }
                    }
                }
            }
        }
    }
}

/*
<div class="profile-page">

  <div class="container">
    <div class="row">

        <div class="article-preview">
          <div class="article-meta">
            <a href=""><img src="http://i.imgur.com/Qr71crq.jpg" /></a>
            <div class="info">
              <a href="" class="author">Eric Simons</a>
              <span class="date">January 20th</span>
            </div>
            <button class="btn btn-outline-primary btn-sm pull-xs-right">
              <i class="ion-heart"></i> 29
            </button>
          </div>
          <a href="" class="preview-link">
            <h1>How to build webapps that scale</h1>
            <p>This is the description for the post.</p>
            <span>Read more...</span>
          </a>
        </div>

        <div class="article-preview">
          <div class="article-meta">
            <a href=""><img src="http://i.imgur.com/N4VcUeJ.jpg" /></a>
            <div class="info">
              <a href="" class="author">Albert Pai</a>
              <span class="date">January 20th</span>
            </div>
            <button class="btn btn-outline-primary btn-sm pull-xs-right">
              <i class="ion-heart"></i> 32
            </button>
          </div>
          <a href="" class="preview-link">
            <h1>The song you won't ever stop singing. No matter how hard you try.</h1>
            <p>This is the description for the post.</p>
            <span>Read more...</span>
            <ul class="tag-list">
              <li class="tag-default tag-pill tag-outline">Music</li>
              <li class="tag-default tag-pill tag-outline">Song</li>
            </ul>
          </a>
        </div>


      </div>

    </div>
  </div>

</div>
 */