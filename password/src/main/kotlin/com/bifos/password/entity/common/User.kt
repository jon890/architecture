package com.bifos.password.entity.common

import com.bifos.password.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class User(
    @Column(name = "user_id", unique = true, nullable = false)
    val userId: String,
    val password: String,
) : BaseEntity() {
}