package com.bifos.recommend.repository

import com.bifos.recommend.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
}