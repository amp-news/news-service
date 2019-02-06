package com.amp.news.user.service

import com.amp.news.user.domain.User
import feign.Headers
import feign.Param
import feign.RequestLine
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Service

/**
 * Service to fetch information about Users from Account Management Service
 * @user Ilya Snitavets
 */
@Service
@FeignClient(name = "accounts-service")
interface UserService {

    @Headers("Content-Type: application/json")
    @RequestLine("GET /users/{id}")
    fun loadById(@Param("id") id: Long): User

}
