package com.bifos.password.service

import com.bifos.password.controller.dto.UserRegisterRequest
import com.bifos.password.entity.common.User
import com.bifos.password.repository.common.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    fun getByUserId(userId: String): User? {
        return userRepository.findByUserId(userId)
    }

    @Transactional
    fun register(registerRequest: UserRegisterRequest): User {
        val newUser = registerRequest.toUserEntity(passwordEncoder)
        val isExistUserId = userRepository.countByUserId(newUser.userId) > 0
        if (isExistUserId) {
            throw IllegalStateException("이미 존재하는 아이디 입니다")
        }

        userRepository.save(newUser)
        return newUser
    }
}