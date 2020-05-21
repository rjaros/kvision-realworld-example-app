package io.realworld.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(val user: User)

@Serializable
data class ProfileDto(val profile: User)

@Serializable
data class User(
    val email: String? = null,
    val token: String? = null,
    val username: String? = null,
    val password: String? = null,
    val bio: String? = null,
    val image: String? = null,
    val following: Boolean? = null
)
