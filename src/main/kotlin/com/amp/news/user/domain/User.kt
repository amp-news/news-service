package com.amp.news.user.domain

data class User(
        val id: Long,
        val firstName: String? = null,
        val lastName: String? = null
)
