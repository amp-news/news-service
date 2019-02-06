package com.amp.news.comment.repository.specification

import com.amp.news.article.domain.Article
import com.amp.news.comment.domain.Comment
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class CommentByArticleSpecification(private val article: Article) : Specification<Comment> {

  override fun toPredicate(
    root: Root<Comment>,
    query: CriteriaQuery<*>,
    criteriaBuilder: CriteriaBuilder
  ): Predicate = criteriaBuilder.equal(root.get<String>(Comment::article.name), article)
}
