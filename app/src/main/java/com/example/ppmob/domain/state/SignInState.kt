package com.example.ppmob.domain.state

data class SignInState(
    val email: String = "",
    val password: String = "",
    var errorEmail:Boolean = false,
    var errorPassword:Boolean = false,
    val passwordVisible:Boolean = false
)