package io.realworld

import kotlinx.coroutines.await
import kotlinx.serialization.Serializable
import pl.treksoft.jquery.JQueryAjaxSettings
import pl.treksoft.jquery.JQueryXHR
import pl.treksoft.kvision.rest.HttpMethod
import pl.treksoft.kvision.rest.RestClient

@Serializable
data class UserDto(val user: User)

object Api {

    const val API_URL = "https://conduit.productionready.io/api"

    private val restClient = RestClient()

    private fun authRequest(xhr: JQueryXHR, @Suppress("UNUSED_PARAMETER") settings: JQueryAjaxSettings): Boolean {
        ConduitManager.getJwtToken()?.let {
            xhr.setRequestHeader("Authorization", "Token $it")
        }
        return true
    }

    suspend fun login(email: String?, password: String?): User {
        return restClient.call<UserDto, UserDto>(
            "$API_URL/users/login",
            UserDto(User(email = email, password = password)),
            HttpMethod.POST
        ).await().user
    }

    suspend fun user(): User {
        return restClient.call<UserDto>(
            "$API_URL/user",
            beforeSend = ::authRequest
        ).await().user
    }
}
