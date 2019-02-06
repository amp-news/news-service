package com.amp.news.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered.HIGHEST_PRECEDENCE
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(HIGHEST_PRECEDENCE)
class SecurityConfig : WebSecurityConfigurerAdapter() {

  @Autowired
  private val authenticationFilter: JwtAuthenticationFilter? = null

  override fun configure(http: HttpSecurity) {
    http.cors()
      .disable()
      .csrf()
      .disable()
      .httpBasic()
      .disable()
      .formLogin()
      .disable()

    http.authorizeRequests()
      .anyRequest()
      .permitAll()
      .and()
      .exceptionHandling()

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
  }
}
