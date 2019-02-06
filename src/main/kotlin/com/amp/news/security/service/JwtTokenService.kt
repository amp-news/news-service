package com.amp.news.security.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.lang.Strings
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class JwtTokenService() : TokenService {

  private val tokenType = "Bearer"

  override fun getTokenClaims(token: String?): Claims {
    val i = token?.lastIndexOf('.')
    val withoutSignature = i?.plus(1)?.let { token.substring(0, it) };
    return Jwts.parser()
      .parseClaimsJwt(withoutSignature)
      .body
  }

  override fun retrieveToken(request: HttpServletRequest): String? {
    var token: String? = null

    val bearerToken = request.getHeader("Authorization")
    if (Strings.hasText(bearerToken) && bearerToken.startsWith(tokenType)) {
      token = bearerToken.replace(tokenType, "").trim { it <= ' ' }
    }

    return token
  }

}