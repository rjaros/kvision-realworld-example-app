package io.realworld.layout

import io.realworld.ConduitManager
import io.realworld.ConduitState
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.html.li
import pl.treksoft.kvision.html.link
import pl.treksoft.kvision.html.nav
import pl.treksoft.kvision.html.ul

fun Container.pagination(state: ConduitState) {
    if (state.articlesCount > 10) {
        nav {
            ul(className = "pagination") {
                val numberOfPages = ((state.articlesCount - 1) / 10) + 1
                for (page in 0 until numberOfPages) {
                    val className = if (page == state.selectedPage) "page-item active" else "page-item"
                    li(className = className) {
                        link("${page + 1}", "", className = "page-link").onClick { e ->
                            e.preventDefault()
                            ConduitManager.selectPage(page)
                        }
                    }
                }
            }
        }
    }
}