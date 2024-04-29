package com.bifos.recommend.controller.dto

import com.bifos.recommend.entity.User

data class UserRegisterRequest(
    val userId: String,
    val password: String,
    val recommendUserId: String?
) {

    fun toUserEntity(): User {
        return User(userId, password)
    }
}