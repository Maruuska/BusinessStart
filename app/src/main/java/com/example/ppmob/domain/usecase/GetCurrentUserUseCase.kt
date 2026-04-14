package com.example.ppmob.domain.usecase

import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.model.User
import com.example.ppmob.domain.repository.UserRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(): Rezult<User> {
        return userRepository.getCurrentUser()
    }
}