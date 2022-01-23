package com.sumfi.playground.core.app.user.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Table(value = "user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userNo: Int = 0,
    val name: String,
    val age: Int
)

data class UserRequest(
    val name: String,
    val age: Int,
)
