package com.amp.news.comment.service

import com.amp.news.article.domain.Article
import com.amp.news.comment.domain.Comment
import com.amp.news.comment.repository.CommentRepository
import com.amp.news.comment.repository.specification.CommentByArticleSpecification
import com.amp.news.core.service.BaseService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class CommentService(private val commentRepository: CommentRepository) :
  BaseService<Comment>(commentRepository) {

  fun findCommentsByArticle(size: Int, offset: Int, article: Article) = commentRepository.findAll(
    CommentByArticleSpecification(article),
    PageRequest.of(offset, size, Sort(Sort.Direction.DESC, Comment::created.name))
  )
}
