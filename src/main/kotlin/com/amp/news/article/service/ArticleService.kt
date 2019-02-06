package com.amp.news.article.service

import com.amp.news.article.domain.Article
import com.amp.news.article.repository.ArticleRepository
import com.amp.news.comment.repository.CommentRepository
import com.amp.news.comment.repository.specification.CommentByArticleSpecification
import com.amp.news.core.service.BaseService
import com.amp.news.tag.repository.TagRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.Optional
import javax.transaction.Transactional

@Service
class ArticleService(
  private val articleRep: ArticleRepository,
  private val tagRep: TagRepository,
  private val commentRepository: CommentRepository
) : BaseService<Article>(articleRep) {

  @Transactional
  override fun save(t: Article): Article {
    t.tags = tagRep.saveAll(t.tags).toHashSet()
    val article = articleRep.save(t)
    article.commentsCount = commentRepository.count(CommentByArticleSpecification(article))
    return article
  }

  override fun findById(id: Long): Optional<Article> {
    val articleOpt = super.findById(id);
    if (articleOpt.isPresent) {
      articleOpt.get().commentsCount =
        commentRepository.count(CommentByArticleSpecification(articleOpt.get()))
    }
    return articleOpt
  }

  @Transactional
  override fun delete(id: Long): Boolean {
    val entity = super.findById(id).orElse(null);
    if (entity != null) {
      commentRepository.deleteAll(entity.comments)
      articleRep.delete(entity)
      return true
    }
    return false
  }

  override fun findAll(size: Int, offset: Int): Page<Article> {
    return articleRep.findAll(PageRequest.of(offset, size, Sort(Sort.Direction.DESC, Article::created.name)))
  }
}
