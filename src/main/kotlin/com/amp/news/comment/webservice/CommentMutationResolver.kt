package com.amp.news.comment.webservice

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.amp.news.article.service.ArticleService
import com.amp.news.comment.domain.Comment
import com.amp.news.comment.service.CommentService
import com.amp.news.comment.webservice.validation.CommentResolverValidator
import com.amp.news.core.service.AuthService
import com.amp.news.core.util.now
import com.amp.news.core.webservice.exception.ForbiddenException
import com.amp.news.core.webservice.exception.NotFoundException
import com.amp.news.core.webservice.exception.ValidationException
import graphql.execution.DataFetcherResult
import graphql.schema.DataFetchingEnvironment
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

/**
 * Resolver for top mutation of Comment entity.
 *
 * Security Should be checked there.
 * For now guests cannot use mutation methods of Article entity
 *
 * Methods can have last argument to be able to access context
 * @see DataFetchingEnvironment
 * @user Ilya Snitavets
 */
@Component
class CommentMutationResolver(
  private val commentService: CommentService,
  private val articleService: ArticleService,
  private val authService: AuthService,
  private val validator: CommentResolverValidator
) : GraphQLMutationResolver {

  @PreAuthorize("hasAnyRole('ROLE_JMP_USER', 'ROLE_ADMIN', 'ROLE_MENTOR', 'ROLE_MENTEE')")
  fun createComment(text: String, articleId: Long): DataFetcherResult<Comment> {
    val user = authService.getCurrentUser()
      ?: throw ForbiddenException("Only authenticated user can do this")
    val messages = validator.validateCommentText(text)
    if (messages.isNotEmpty()) {
      throw ValidationException("Input parameters invalid", messages)
    }
    val article = articleService.findById(articleId)
      .orElseThrow { throw NotFoundException("Article with id=$articleId not found") }

    val comment = Comment(
      text = text,
      article = article,
      authorId = user.id
    )

    return DataFetcherResult(commentService.save(comment), emptyList())
  }

  @PreAuthorize("hasAnyRole('ROLE_JMP_USER', 'ROLE_ADMIN', 'ROLE_MENTOR', 'ROLE_MENTEE')")
  fun updateComment(id: Long, text: String): Comment? {
    val messages = validator.validateCommentText(text)
    if (messages.isNotEmpty()) {
      throw ValidationException("Input parameters invalid", messages)
    }
    val comment =
      commentService.findById(id).orElseThrow { NotFoundException("Comment with id=$id not found") }

    comment.text = text
    comment.updated = now()
    return commentService.save(comment)
  }

  @PreAuthorize("hasAnyRole('ROLE_JMP_USER', 'ROLE_ADMIN', 'ROLE_MENTOR', 'ROLE_MENTEE')")
  fun deleteComment(id: Long): Boolean {
    val user = authService.getCurrentUser()
      ?: throw ForbiddenException("Only authenticated user can do this")
    val comment =
      commentService.findById(id).orElseThrow { NotFoundException("Comment with id=$id not found") }

    if (comment.authorId != user.id) {
      throw ForbiddenException("You cannot delete this comment")
    }
    return commentService.delete(id)
  }
}
