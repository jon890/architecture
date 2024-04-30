package com.bifos.password.controller.dto

import com.bifos.password.entity.User

data class UserRegisterRequest(
    val userId: String,
    val password: String,
    val recommendUserId: String?
) {

    fun toUserEntity(): User {
        return User(userId, password)
    }
}