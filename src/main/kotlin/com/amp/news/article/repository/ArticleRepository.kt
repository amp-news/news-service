package com.amp.news.article.repository

import com.amp.news.article.domain.Article
import com.amp.news.core.repository.BaseRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : BaseRepository<Article>
