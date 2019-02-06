package com.amp.news.article.webservice

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.amp.news.article.domain.Article
import com.amp.news.article.service.ArticleService
import com.amp.news.article.webservice.validation.ArticleResolverValidator
import com.amp.news.core.service.AuthService
import com.amp.news.core.util.now
import com.amp.news.core.webservice.exception.ForbiddenException
import com.amp.news.core.webservice.exception.NotFoundException
import com.amp.news.core.webservice.exception.ValidationException
import com.amp.news.notification.NewsPublishedNotification
import com.amp.news.notification.NotificationProducer
import com.amp.news.tag.domain.Tag
import com.amp.news.tag.service.TagService
import graphql.schema.DataFetchingEnvironment
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

/**
 * Resolver for top mutation of Article entity.
 *
 * Security Should be check there.
 * For now guests cannot use mutation methods of Article entity
 *
 * Methods can have last argument to be able to access context
 * @see DataFetchingEnvironment
 * @user Ilya Snitavets
 */
@Component
class ArticleMutationResolver(
  private val articleService: ArticleService,
  private val notificationProducer: NotificationProducer,
  private val tagService: TagService,
  private val authService: AuthService,
  private val validator: ArticleResolverValidator
) : GraphQLMutationResolver {

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MENTOR')")
  fun createArticle(input: ArticleInput): Article {
    val user = authService.getCurrentUser()
      ?: throw ForbiddenException("Only authenticated user can do this")
    val messages = validator.validateArticleInput(input)
    if (messages.isNotEmpty()) {
      throw ValidationException("Input parameters invalid", messages)
    }
    val article = Article(
      brief = input.brief,
      text = input.text,
      title = input.title,
      authorId = user.id
    )
    article.tags = mergeTagsByName(input.tags)
    val createdArticle = articleService.save(article)
    notificationProducer.sendEvent(
      NewsPublishedNotification(
        createdArticle.authorId,
        createdArticle.id,
        createdArticle.title,
        createdArticle.brief
      )
    )
    return createdArticle
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MENTOR')")
  fun updateArticle(id: Long, input: ArticleInput): Article {
    val messages = validator.validateArticleInput(input)
    if (messages.isNotEmpty()) {
      throw ValidationException("Input parameters invalid", messages)
    }
    val article = articleService.findById(id)
      .orElseThrow { NotFoundException("Article with id=$id not found") }
    article.brief = input.brief
    article.text = input.text
    article.title = input.title
    article.tags = mergeTagsByName(input.tags)
    article.updated = now()
    return articleService.save(article)
  }

  private fun mergeTagsByName(tagNames: Set<String>): Set<Tag> {
    val tags = tagService.findTagsByNames(tagNames)
    return tagNames.map { tagName ->
      tags.find { it.name == tagName } ?: Tag(name = tagName)
    }.toHashSet()
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MENTOR')")
  fun deleteArticle(id: Long): Boolean {
    val user = authService.getCurrentUser()
      ?: throw ForbiddenException("Only authenticated user can do this")
    val article = articleService.findById(id)
      .orElseThrow { NotFoundException("Article with id=$id not found") }
    if (article.authorId != user.id) {
      throw ForbiddenException("You cannot delete this article")
    }
    return articleService.delete(id)
  }
}
