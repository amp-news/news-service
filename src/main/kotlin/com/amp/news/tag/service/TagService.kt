package com.amp.news.tag.service

import com.amp.news.core.service.BaseService
import com.amp.news.tag.domain.Tag
import com.amp.news.tag.repository.TagRepository
import com.amp.news.tag.repository.specification.TagByNamesSpecification
import com.amp.news.tag.repository.specification.TagExcludeNamesSpecification
import com.amp.news.tag.repository.specification.TagLikeNameSpecification
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class TagService(private val tagRepository: TagRepository) : BaseService<Tag>(tagRepository) {

  fun findTagsByNames(names: Set<String>): Set<Tag> {
    val spec = if (names.isNotEmpty()) TagByNamesSpecification(names) else null
    return tagRepository.findAll(spec).toHashSet()
  }

  fun findTags(limit: Int, search: String? = null, excludeTags: Set<String>?): Page<Tag> {
    val spec = search?.let { TagLikeNameSpecification(search) }
    val etSpec =
    if (excludeTags != null && excludeTags.isNotEmpty()) {
      TagExcludeNamesSpecification(excludeTags)
    } else {
      null
    }
    val resSpec = if (spec != null && etSpec != null) {
      spec.and(etSpec)
    } else spec ?: etSpec
    return tagRepository.findAll(resSpec, PageRequest.of(0, limit))
  }
}
