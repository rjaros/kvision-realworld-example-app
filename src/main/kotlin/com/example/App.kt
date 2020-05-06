package com.example

import pl.treksoft.kvision.Application
import pl.treksoft.kvision.html.div
import pl.treksoft.kvision.panel.root
import pl.treksoft.kvision.require
import pl.treksoft.kvision.startApplication

class App : Application() {
    init {
        require("css/kvapp.css")
    }

    override fun start() {
        root("kvapp") {
            div("This is a localized message.")
            // TODO
        }
    }
}

fun main() {
    startApplication(::App)
}
