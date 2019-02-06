package com.amp.news.article.webservice

/**
 * Data Transfer class for creation/modification Article entity
 * @user Ilya Snitavets
 */
data class ArticleInput(
  val title: String,
  val brief: String?,
  val text: String?,
  val tags: Set<String> = emptySet()
)
