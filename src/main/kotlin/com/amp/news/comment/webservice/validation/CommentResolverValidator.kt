package com.amp.news.comment.webservice.validation

import com.amp.news.core.webservice.validation.BaseValidator
import org.springframework.stereotype.Service

@Service
class CommentResolverValidator : BaseValidator() {

  object Const {
    const val MAX_COMMENT_LENGTH = 1000
    const val MIN_COMMENT_LENGTH = 1
  }

  fun validateCommentText(text: String): Map<String, String> {
    val messages = hashMapOf<String, String>()
    if (text.length !in Const.MIN_COMMENT_LENGTH..Const.MAX_COMMENT_LENGTH) {
      messages["text"] =
        "Length should be between ${Const.MIN_COMMENT_LENGTH} and ${Const.MAX_COMMENT_LENGTH}"
    }
    return messages
  }
}
