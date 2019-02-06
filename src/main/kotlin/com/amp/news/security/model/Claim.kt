package com.amp.news.security.model

import java.util.stream.Stream

enum class Claim constructor(val claimName: String) {

  USER_EMAIL("email"),
  USER_STATUS("status"),
  USER_ROLE("role"),
  FROM_EXTERNAL("external");

  companion object {

    fun fromName(fromName: String): Claim {
      return Stream.of(*Claim.values())
        .filter { claim -> claim.claimName == fromName }
        .findFirst()
        .orElseThrow<IllegalArgumentException>({ IllegalArgumentException() })
    }
  }
}