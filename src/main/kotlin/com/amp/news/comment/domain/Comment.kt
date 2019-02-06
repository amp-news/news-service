package com.amp.news.comment.domain

import com.amp.news.article.domain.Article
import com.amp.news.core.domain.BaseEntity
import com.amp.news.core.util.now
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
data class Comment(

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  override var id: Long? = null,

  var text: String,

  @Column(name = "author_id")
  val authorId: Long,

  @ManyToOne(targetEntity = Article::class)
  val article: Article,

  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  val created: Date = now(),

  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  var updated: Date = now()
) : BaseEntity
