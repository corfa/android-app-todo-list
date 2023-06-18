package com.example.android_app.data

data class Credentials(
    val username: String,
    val password: String
)

data class Token(
    val token: String
)


data class Item(
    val id: String?,
    val done: Boolean,
    val desc: String
)

data class Doing(
    val desc: String,
)

data class TokenValid(
    val valid: Boolean
    )
