package com.amp.news.security.service

import io.jsonwebtoken.Claims
import javax.servlet.http.HttpServletRequest

interface TokenService {

  fun retrieveToken(request: HttpServletRequest): String?

  fun getTokenClaims(token: String?): Claims
}