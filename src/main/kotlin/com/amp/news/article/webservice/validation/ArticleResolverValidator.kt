package com.amp.news.article.webservice.validation

import com.amp.news.article.webservice.ArticleInput
import com.amp.news.core.webservice.validation.BaseValidator
import org.springframework.stereotype.Service

@Service
class ArticleResolverValidator : BaseValidator() {

  object Const {
    const val MIN_TITLE_LENGTH = 1
    const val MAX_TITLE_LENGTH = 200
    const val MAX_BRIEF_LENGTH = 500
    const val MAX_TAG_LENGTH = 100
  }

  fun validateArticleInput(articleInput: ArticleInput): Map<String, String> {
    val messages = hashMapOf<String, String>()
    if (articleInput.title.length !in Const.MIN_TITLE_LENGTH..Const.MAX_TITLE_LENGTH) {
      messages["title"] =
        "Length should between ${Const.MIN_TITLE_LENGTH} and ${Const.MAX_TITLE_LENGTH}"
    }
    if (articleInput.brief?.length ?: 0 > Const.MAX_BRIEF_LENGTH) {
      messages["brief"] = "Length should less or equals than ${Const.MAX_BRIEF_LENGTH}"
    }
    if (articleInput.tags.any { it.length > Const.MAX_TAG_LENGTH }) {
      messages["tag"] = "Length of each should be less or equals than ${Const.MAX_TAG_LENGTH}"
    }
    return messages
  }
}
