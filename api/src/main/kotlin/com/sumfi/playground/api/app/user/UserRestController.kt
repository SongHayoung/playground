package com.sumfi.playground.api.app.user

import com.sumfi.playground.core.app.user.model.User
import com.sumfi.playground.core.app.user.model.UserRequest
import com.sumfi.playground.core.app.user.service.UserService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
class UserRestController(
    private val userService: UserService
) {
    @GetMapping(value = ["/user/{userNo}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUser(@PathVariable userNo: Int): Mono<User> {
        return userService.getUser(userNo)
    }

    @PostMapping(value = ["/user"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun postUser(@RequestBody request: UserRequest): Mono<User> {
        return userService.saveUser(Mono.just(request))
    }
}
