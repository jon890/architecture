package com.bifos.recommend.repository

import com.bifos.recommend.entity.UserRecommend
import org.springframework.data.jpa.repository.JpaRepository

interface UserRecommendRepository : JpaRepository<UserRecommend, Long> {
}