package com.amp.news.tag.webservice

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.amp.news.core.webservice.exception.ValidationException
import com.amp.news.tag.domain.Tag
import com.amp.news.tag.service.TagService
import com.amp.news.tag.webservice.validation.TagResolverValidator
import graphql.schema.DataFetchingEnvironment
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

/**
 * Resolver for top queries of Tag entity.
 *
 * Security Should be check there.
 * For now all user roles can call query methods
 *
 * Methods can have last argument to be able to access context
 * @see DataFetchingEnvironment
 * @user Ilya Snitavets
 */
@Component
class TagQueryResolver(
  private val tagService: TagService,
  private val validator: TagResolverValidator
) : GraphQLQueryResolver {

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MENTOR')")
  fun tags(limit: Int, search: String?, excludeTags: Set<String>?): List<Tag> {
    val messages = validator.validateLimitAndSearch(limit, search)
    if (messages.isNotEmpty()) {
      throw ValidationException("Input parameters invalid", messages)
    }
    return tagService.findTags(limit, search, excludeTags).toList()
  }
}
