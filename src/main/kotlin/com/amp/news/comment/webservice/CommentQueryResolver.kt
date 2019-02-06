package com.amp.news.comment.webservice

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.amp.news.article.service.ArticleService
import com.amp.news.comment.domain.Comment
import com.amp.news.comment.service.CommentService
import com.amp.news.comment.webservice.validation.CommentResolverValidator
import com.amp.news.core.webservice.PageableResult
import com.amp.news.core.webservice.exception.NotFoundException
import com.amp.news.core.webservice.exception.ValidationException
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

/**
 * Resolver for top queries of Comment entity.
 *
 * Security Should be check there.
 * For now all user roles can call query methods
 *
 * Methods can have last argument to be able to access context
 * @see DataFetchingEnvironment
 * @user Ilya Snitavets
 */
@Component
class CommentQueryResolver(
  private val commentService: CommentService,
  private val articleService: ArticleService,
  private val validator: CommentResolverValidator
) : GraphQLQueryResolver {

  fun comments(size: Int, offset: Int, articleId: Long): List<Comment> {
    val messages = validator.validatePageParameters(size, offset)
    if (messages.isNotEmpty()) {
      throw ValidationException("Input parameters invalid", messages)
    }
    val article = articleService.findById(articleId)
      .orElseThrow { NotFoundException("Article with id=$articleId not found") }

    return commentService.findCommentsByArticle(size, offset, article).toList()
  }
}
