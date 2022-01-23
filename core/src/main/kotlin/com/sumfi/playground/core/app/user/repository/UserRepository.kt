package com.sumfi.playground.core.app.user.repository

import com.sumfi.playground.core.app.user.model.User
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: R2dbcRepository<User, Int> {
}
