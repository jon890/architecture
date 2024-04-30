package com.bifos.password.controller.dto

import com.bifos.password.entity.common.User
import org.springframework.security.crypto.password.PasswordEncoder

data class UserRegisterRequest(
    val userId: String,
    val password: String,
    val recommendUserId: String?
) {

    fun toUserEntity(passwordEncoder: PasswordEncoder): User {
        return User(userId, passwordEncoder.encode(password))
    }
}