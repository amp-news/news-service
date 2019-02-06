package com.amp.news.security

import com.amp.news.security.model.Claim
import com.amp.news.security.service.TokenService
import io.jsonwebtoken.Claims
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.Collections
import java.util.Objects
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter(private val tokenService: TokenService) : OncePerRequestFilter() {

  override fun doFilterInternal(
    request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
  ) {

    val jwt = tokenService.retrieveToken(request)
    if (Objects.nonNull(jwt)) {
      val claims = tokenService.getTokenClaims(jwt)

      if (isAuthenticationRequired(claims.get(Claim.USER_EMAIL.claimName, String::class.java))) {
        val authentication = getAuthentication(request, claims)
        SecurityContextHolder.getContext().authentication = authentication
      }
    }

    filterChain.doFilter(request, response)
  }

  private fun getAuthentication(request: HttpServletRequest, claims: Claims): Authentication {

    val role = claims.get(Claim.USER_ROLE.claimName, String::class.java)
    val authentication = UsernamePasswordAuthenticationToken(
      claims.subject.toLong(),
      claims[Claim.USER_EMAIL.claimName],
      Collections.singletonList(SimpleGrantedAuthority(role))
    )
    authentication.details = getAuthenticationDetails(request)

    return authentication
  }

  private fun isAuthenticationRequired(email: String): Boolean {
    val existingAuth = SecurityContextHolder.getContext().authentication

    return if (existingAuth == null || !existingAuth.isAuthenticated) {
      true
    } else existingAuth is UsernamePasswordAuthenticationToken && existingAuth.name != email

  }

  private fun getAuthenticationDetails(request: HttpServletRequest): WebAuthenticationDetails {
    return WebAuthenticationDetailsSource().buildDetails(request)
  }

}
