package com.amp.news.tag.repository.specification

import com.amp.news.tag.domain.Tag
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class TagLikeNameSpecification(private val searchName: String) : Specification<Tag> {

  override fun toPredicate(
    root: Root<Tag>,
    query: CriteriaQuery<*>,
    cb: CriteriaBuilder
  ): Predicate = cb.like(root.get<String>(Tag::name.name), "%$searchName%")
}
