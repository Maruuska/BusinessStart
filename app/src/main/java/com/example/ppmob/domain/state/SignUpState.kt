package com.example.ppmob.domain.state

data class SignUpState (
    val email: String = "",
    var errorEmail:Boolean = false,
    val password: String = "",
    var errorPassword:Boolean = false,
    val confirmPassword: String = "",
)