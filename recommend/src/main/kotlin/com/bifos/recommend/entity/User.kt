package com.bifos.recommend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class User(
    @Column(name = "id", unique = true, nullable = false)
    val id: String,
    val password: String,
) : BaseEntity() {
}