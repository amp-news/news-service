package com.amp.news.tag.webservice.validation

import com.amp.news.core.webservice.validation.BaseValidator
import org.springframework.stereotype.Service

@Service
class TagResolverValidator : BaseValidator() {

  object Const {
    const val MIN_LIMIT_SIZE = 1
    const val MAX_LIMIT_SIZE = 100
    const val MAX_SEARCH_LENGTH = 100
  }

  fun validateLimitAndSearch(limit: Int, search: String?): Map<String, String> {
    val messages = hashMapOf<String, String>()
    if (limit !in Const.MIN_LIMIT_SIZE..Const.MAX_LIMIT_SIZE) {
      messages["limit"] = "Should be between ${Const.MIN_LIMIT_SIZE} and ${Const.MAX_LIMIT_SIZE}"
    }
    if (search?.length ?: 0 > Const.MAX_SEARCH_LENGTH) {
      messages["search"] = "Length should be less or equals ${Const.MAX_SEARCH_LENGTH}"
    }
    return messages
  }
}
