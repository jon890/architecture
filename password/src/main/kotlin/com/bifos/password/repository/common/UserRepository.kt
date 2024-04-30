package com.bifos.password.repository.common

import com.bifos.password.entity.common.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findByUserId(userId: String): User?

    fun countByUserId(userId: String): Int
}