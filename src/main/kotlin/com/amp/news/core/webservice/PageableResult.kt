package com.amp.news.core.webservice

import com.amp.news.core.domain.BaseEntity
import org.springframework.data.domain.Page

data class PageableResult<T: BaseEntity>(
  val pageInfo: PageInfo,
  val content: List<T>
){
  constructor(page: Page<T>) :
    this(PageInfo(page.totalPages, page.totalElements, page.isFirst, page.isLast, page.number), page.content)
}