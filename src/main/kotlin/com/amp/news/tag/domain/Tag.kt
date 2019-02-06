package com.amp.news.tag.domain

import com.amp.news.core.domain.BaseEntity
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Tag(

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  override var id: Long? = null,

  val name: String

) : BaseEntity
