package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.AuthResponseDomain
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.repository.UserRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): Rezult<AuthResponseDomain> {
        return userRepository.signIn(email, password)
    }
}