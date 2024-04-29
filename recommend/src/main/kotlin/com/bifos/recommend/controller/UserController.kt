package com.bifos.recommend.controller

import com.bifos.recommend.controller.dto.UserRegisterRequest
import com.bifos.recommend.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("{id}")
    fun getUserById(@PathVariable id: String): ResponseEntity<*> {
        val maybeUser = userService.getByUserId(id)
        return ResponseEntity.ofNullable(maybeUser)
    }

    @PostMapping
    fun register(@RequestBody requestDto: UserRegisterRequest): ResponseEntity<*> {
        val newUser = userService.register(requestDto)
        return ResponseEntity
            .created(URI.create("/api/v1/users/${newUser.userId}"))
            .body(newUser)
    }
}