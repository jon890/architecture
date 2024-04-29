package com.bifos.recommend.service

import com.bifos.recommend.entity.User
import com.bifos.recommend.entity.UserRecommend
import com.bifos.recommend.repository.UserRecommendRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserRecommendService(
    private val userRecommendRepository: UserRecommendRepository
) {

    @Transactional
    fun recommend(fromUser: User, toUser: User): UserRecommend {
        val userRecommend = UserRecommend(fromUser, toUser)
        return userRecommendRepository.save(userRecommend)
    }
}