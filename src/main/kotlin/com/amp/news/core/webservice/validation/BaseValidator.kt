package com.amp.news.core.webservice.validation

abstract class BaseValidator {
  object Const {
    const val MAX_PAGE_SIZE = 100
    const val MIN_PAGE_SIZE = 1
    const val MIN_PAGE_COUNT = 0
  }

  fun validatePageParameters(size: Int, offset: Int): Map<String, String> {
    val messages = hashMapOf<String, String>()
    if (size !in Const.MIN_PAGE_SIZE..Const.MAX_PAGE_SIZE) {
      messages["size"] = "Should be between ${Const.MIN_PAGE_SIZE} and ${Const.MAX_PAGE_SIZE}"
    }
    if (offset < Const.MIN_PAGE_COUNT) {
      messages["offset"] = "Should be grater of equals then ${Const.MIN_PAGE_COUNT}"
    }
    return messages
  }
}
