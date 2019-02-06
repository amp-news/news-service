package com.amp.news.notification

data class NewsPublishedNotification(
    val authorId: Long,
    val newsId: Long?,
    val title: String,
    var brief: String? = null
)