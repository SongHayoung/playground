package com.sumfi.playground.core.app.user.service

import com.sumfi.playground.core.app.user.model.User
import com.sumfi.playground.core.app.user.model.UserRequest
import com.sumfi.playground.core.app.user.repository.UserRepository
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserService(
    private val userRepository: UserRepository
) {

   @Transactional("slaveTransactionManager", readOnly = true)
    fun getUser(userNo: Int): Mono<User> {
        return userRepository.findById(userNo)
    }

    @Transactional("masterTransactionManager")
    fun saveUser(userRequest: Mono<UserRequest>): Mono<User> {
        return userRequest.flatMap { req ->
            val user = User(name = req.name, age = req.age)

            userRepository.save(user)
        }
    }
}
