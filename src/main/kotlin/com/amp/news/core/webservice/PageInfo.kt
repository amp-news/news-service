package com.amp.news.core.webservice

data class PageInfo(
  val totalPages: Int,
  val totalElements: Long,
  val isFirst: Boolean,
  val isLast: Boolean,
  val current: Int
)