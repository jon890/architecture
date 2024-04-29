package com.bifos.recommend.controller

import com.bifos.recommend.service.UserService
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) {
}