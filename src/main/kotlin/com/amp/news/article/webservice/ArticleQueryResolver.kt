package com.amp.news.article.webservice

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.amp.news.article.domain.Article
import com.amp.news.article.service.ArticleService
import com.amp.news.article.webservice.model.ArticlePageableResult
import com.amp.news.article.webservice.validation.ArticleResolverValidator
import com.amp.news.core.webservice.PageableResult
import com.amp.news.core.webservice.exception.ValidationException
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

/**
 * Resolver for top queries of Article entity.
 *
 * Security Should be check there.
 * For now all user roles can call query methods
 *
 * Methods can have last argument to be able to access context
 * @see DataFetchingEnvironment
 * @user Ilya Snitavets
 */
@Component
class ArticleQueryResolver(
  private val articleService: ArticleService,
  private val validator: ArticleResolverValidator
) : GraphQLQueryResolver {

  fun articles(size: Int, offset: Int): ArticlePageableResult {
    val messages = validator.validatePageParameters(size, offset)
    if (messages.isNotEmpty()) {
      throw ValidationException("Input parameters invalid", messages)
    }
    return ArticlePageableResult(articleService.findAll(size, offset))
  }

  fun article(id: Long) = articleService.findById(id)
}
