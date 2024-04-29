package com.bifos.recommend.service

import com.bifos.recommend.const.RedisKeyConst
import com.bifos.recommend.entity.User
import com.bifos.recommend.entity.UserRecommend
import com.bifos.recommend.repository.UserRecommendRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserRecommendService(
    private val userRecommendRepository: UserRecommendRepository,
    private val redisService: RedisService
) {
    private val logger = LoggerFactory.getLogger(UserRecommendService::class.java)

    @Transactional
    fun recommend(fromUser: User, toUser: User): UserRecommend {
        val userRecommend = UserRecommend(fromUser, toUser)
        redisService.minusLongValue(RedisKeyConst.EVENT_RECOMMEND_BUDGET_KEY, 10000)
        return userRecommendRepository.save(userRecommend)
    }
}