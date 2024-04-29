package com.bifos.recommend.service

import com.bifos.recommend.controller.dto.UserRegisterRequest
import com.bifos.recommend.entity.User
import com.bifos.recommend.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userRecommendService: UserRecommendService
) {

    fun getByUserId(userId: String): User? {
        return userRepository.findByUserId(userId)
    }

    @Transactional
    fun register(registerRequest: UserRegisterRequest): User {
        val newUser = registerRequest.toUserEntity()
        val isExistUserId = userRepository.countByUserId(newUser.userId) > 0
        if (isExistUserId) {
            throw IllegalStateException("이미 존재하는 아이디 입니다")
        }

        userRepository.save(newUser)

        if (registerRequest.recommendUserId != null) {
            val fromUser = getByUserId(registerRequest.recommendUserId)
            if (fromUser == null) {
                throw IllegalStateException("추천인이 존재하지 않습니다")
            }

            userRecommendService.recommend(fromUser, newUser)
        }

        return newUser
    }
}