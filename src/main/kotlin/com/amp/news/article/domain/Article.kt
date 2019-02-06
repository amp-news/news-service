package com.amp.news.article.domain

import com.amp.news.comment.domain.Comment
import com.amp.news.core.domain.BaseEntity
import com.amp.news.core.util.now
import com.amp.news.tag.domain.Tag
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.Date
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
data class Article(

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  override var id: Long? = null,

  var title: String,

  var brief: String?,

  var text: String?,

  @Column(name = "author_id")
  var authorId: Long,

  @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
  @JoinTable(
    name = "article_tag", joinColumns =
    [(JoinColumn(name = "article_id", nullable = false, updatable = false))],
    inverseJoinColumns = [(JoinColumn(
      name = "tag_id",
      nullable = false, updatable = false
    ))]
  )

  var tags: Set<Tag> = emptySet(),

  @OneToMany(targetEntity = Comment::class, mappedBy = "article", orphanRemoval=true)
  var comments: List<Comment> = emptyList(),

  @Transient
  var commentsCount: Long? = 0,

  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  val created: Date = now(),

  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  var updated: Date = now()
) : BaseEntity
