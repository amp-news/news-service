package com.amp.news.core.service

import com.amp.news.core.domain.BaseEntity
import com.amp.news.core.repository.BaseRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
abstract class BaseService<T : BaseEntity>(private val repository: BaseRepository<T>) {

  fun save(t: T): T = repository.saveAndFlush(t)

  fun findAll(size: Int, offset: Int): Page<T> =
    repository.findAll(PageRequest.of(offset, size))

  fun findById(id: Long) = repository.findById(id)

  fun delete(id: Long): Boolean {
    val entity = repository.findById(id);
    return if (entity.isPresent) {
      repository.delete(entity.get());
      true
    } else {
      false
    }
  }
}
