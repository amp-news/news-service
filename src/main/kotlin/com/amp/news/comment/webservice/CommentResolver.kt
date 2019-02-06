package com.amp.news.comment.webservice

import com.coxautodev.graphql.tools.GraphQLResolver
import com.amp.news.comment.domain.Comment
import com.amp.news.user.service.UserService
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

/**
 * Comment Resolver provides fields for Article entity
 *
 * Methods can have last argument to be able to access context
 * @see DataFetchingEnvironment
 * @user Ilya Snitavets
 */

@Component
class CommentResolver(
  private val authorService: UserService
) : GraphQLResolver<Comment> {

  fun author(comment: Comment) = authorService.loadById(comment.authorId)

}
