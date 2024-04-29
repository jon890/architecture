package com.bifos.recommend.entity

import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne

@Entity
class UserRecommend(
    @ManyToOne(optional = false)
    @JoinColumn(name = "from_user_id")
    val fromUser: User,

    @OneToOne(optional = false)
    @JoinColumn(name = "to_user_id", unique = true)
    val toUser: User
) : BaseEntity() {
}