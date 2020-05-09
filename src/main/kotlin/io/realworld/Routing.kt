package io.realworld

import pl.treksoft.navigo.Navigo

enum class View(val url: String) {
    HOME("/"),
    LOGIN("/login"),
    REGISTER("/register"),
    EDITOR("/editor"),
    SETTINGS("/settings")
}

fun Navigo.initialize(): Navigo {
    return on(View.HOME.url, { _ ->
        ConduitManager.homePage()
    }).on(View.LOGIN.url, { _ ->
        ConduitManager.loginPage()
    }).on(View.REGISTER.url, { _ ->
        ConduitManager.registerPage()
    })
}
