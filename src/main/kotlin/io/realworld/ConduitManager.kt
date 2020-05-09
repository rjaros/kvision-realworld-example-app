package io.realworld

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.w3c.dom.get
import org.w3c.dom.set
import pl.treksoft.kvision.redux.createReduxStore
import pl.treksoft.navigo.Navigo
import kotlin.browser.localStorage

object ConduitManager : CoroutineScope by CoroutineScope(Dispatchers.Default + SupervisorJob()) {

    const val JWT_TOKEN = "jwtToken"

    val routing = Navigo(null, true, "#")
    val conduitStore = createReduxStore(::conduitReducer, ConduitState(View.HOME))

    fun initialize() {
        routing.initialize().resolve()
        if (getJwtToken() != null) {
            withProgress {
                try {
                    val user = Api.user()
                    conduitStore.dispatch(ConduitAction.login(user))
                    saveJwtToken(user.token!!)
                } catch (e: Exception) {
                    console.log("Invalid JWT Token")
                    deleteJwtToken()
                }
            }
        }
    }

    fun homePage() {
        conduitStore.dispatch(ConduitAction.homePage)
    }

    fun loginPage() {
        conduitStore.dispatch(ConduitAction.loginPage)
    }

    fun registerPage() {
        conduitStore.dispatch(ConduitAction.registerPage)
    }

    fun login(email: String?, password: String?) {
        withProgress {
            try {
                val user = Api.login(email, password)
                conduitStore.dispatch(ConduitAction.login(user))
                saveJwtToken(user.token!!)
                routing.navigate(View.HOME.url)
            } catch (e: Exception) {
                conduitStore.dispatch(ConduitAction.loginError)
            }
        }
    }

    fun logout() {
        deleteJwtToken()
        conduitStore.dispatch(ConduitAction.logout)
        routing.navigate(View.HOME.url)
    }

    fun saveJwtToken(token: String) {
        localStorage[JWT_TOKEN] = token
    }

    fun deleteJwtToken() {
        localStorage.removeItem(JWT_TOKEN)
    }

    fun getJwtToken(): String? {
        return localStorage[JWT_TOKEN]
    }
}
