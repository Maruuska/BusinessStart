package com.example.ppmob.domain.state

data class SignInState(
    val email: String = "test@test.com",
    val password: String = "12345678",
    var errorEmail:Boolean = false,
    var errorPassword:Boolean = false,
    val passwordVisible:Boolean = false
)