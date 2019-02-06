package com.amp.news.core.service

import com.amp.news.user.domain.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthService() {

  fun isAuthenticated(): Boolean {
    val authentication = SecurityContextHolder.getContext().authentication
    return authentication.isAuthenticated
  }

  fun getCurrentUser(): User? {
    val authentication = SecurityContextHolder.getContext().authentication
    return if (authentication.isAuthenticated) {
      User(
        authentication.principal as Long, authentication.credentials as String?
      )
    } else {
      null
    }
  }
}
