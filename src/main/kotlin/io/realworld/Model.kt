package io.realworld

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String? = null,
    val token: String? = null,
    val username: String? = null,
    val password: String? = null,
    val bio: String? = null,
    val image: String? = null
)
