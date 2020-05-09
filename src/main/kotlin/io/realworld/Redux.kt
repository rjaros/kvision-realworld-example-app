package io.realworld

import pl.treksoft.kvision.redux.RAction

data class ConduitState(
    val view: View,
    val loginError: Boolean = false,
    val user: User? = null
)

sealed class ConduitAction : RAction {
    object homePage : ConduitAction()
    object loginPage : ConduitAction()
    object registerPage : ConduitAction()
    data class login(val user: User) : ConduitAction()
    object loginError : ConduitAction()
    object logout : ConduitAction()
}

fun conduitReducer(state: ConduitState, action: ConduitAction): ConduitState = when (action) {
    is ConduitAction.homePage -> {
        state.copy(view = View.HOME)
    }
    is ConduitAction.loginPage -> {
        state.copy(view = View.LOGIN)
    }
    is ConduitAction.registerPage -> {
        state.copy(view = View.REGISTER)
    }
    is ConduitAction.login -> {
        state.copy(user = action.user)
    }
    is ConduitAction.loginError -> {
        state.copy(user = null, loginError = true)
    }
    is ConduitAction.logout -> {
        state.copy(user = null)
    }
}
