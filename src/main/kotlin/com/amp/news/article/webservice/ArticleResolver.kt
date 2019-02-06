package com.amp.news.article.webservice

import com.coxautodev.graphql.tools.GraphQLResolver
import com.amp.news.article.domain.Article
import com.amp.news.comment.domain.Comment
import com.amp.news.comment.service.CommentService
import com.amp.news.user.service.UserService
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

/**
 * Article Resolver provides fields for Article entity
 * No need to check security. Should be checked in top Query/Mutation methods
 *
 * Methods can have last argument to be able to access context
 * @see DataFetchingEnvironment
 * @user Ilya Snitavets
 */
@Component
class ArticleResolver(
  private val authorService: UserService,
  private val commentService: CommentService
) : GraphQLResolver<Article> {

  fun author(article: Article) = authorService.loadById(article.authorId)

  fun comments(article: Article, size: Int, offset: Int): List<Comment> =
    commentService.findCommentsByArticle(size, offset, article).toList()
}
