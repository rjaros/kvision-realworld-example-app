package io.realworld

import io.realworld.layout.article
import io.realworld.layout.footer
import io.realworld.layout.headerNav
import io.realworld.layout.homePage
import io.realworld.layout.loginPage
import io.realworld.layout.profilePage
import pl.treksoft.kvision.Application
import pl.treksoft.kvision.html.header
import pl.treksoft.kvision.html.main
import pl.treksoft.kvision.pace.Pace
import pl.treksoft.kvision.pace.PaceOptions
import pl.treksoft.kvision.panel.ContainerType
import pl.treksoft.kvision.panel.root
import pl.treksoft.kvision.require
import pl.treksoft.kvision.startApplication
import pl.treksoft.kvision.state.bind

class App : Application() {
    init {
        require("css/kvapp.css")
    }

    override fun start() {
        Pace.init(require("pace-progressbar/themes/green/pace-theme-bounce.css"))
        Pace.setOptions(PaceOptions(manual = true))
        ConduitManager.initialize()
        root("kvapp", containerType = ContainerType.NONE, addRow = false) {
            header {
                bind(ConduitManager.conduitStore) { state ->
                    headerNav(state)
                }
            }
            main {
                bind(ConduitManager.conduitStore) { state ->
                    if (!state.appLoading) {
                        when (state.view) {
                            View.HOME -> {
                                homePage(state)
                            }
                            View.ARTICLE -> {
                                article(state)
                            }
                            View.PROFILE -> {
                                profilePage(state)
                            }
                            View.LOGIN -> {
                                loginPage(state)
                            }
                            View.REGISTER -> {

                            }
                            View.EDITOR -> TODO()
                            View.SETTINGS -> TODO()
                        }
                    }
                }
            }
            footer()
        }
    }
}

fun main() {
    startApplication(::App)
}
