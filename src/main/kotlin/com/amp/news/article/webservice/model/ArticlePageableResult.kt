package com.amp.news.article.webservice.model

import com.amp.news.article.domain.Article
import com.amp.news.core.webservice.PageInfo
import org.springframework.data.domain.Page

data class ArticlePageableResult(
  val pageInfo: PageInfo,
  val content: List<Article>
) {
  constructor(page: Page<Article>) :
    this(PageInfo(page.totalPages, page.totalElements, page.isFirst, page.isLast, page.number), page.content)
}