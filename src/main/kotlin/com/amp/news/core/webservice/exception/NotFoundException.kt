package com.amp.news.core.webservice.exception

import graphql.ErrorType

class NotFoundException(msg: String) : WebserviceException(msg) {
  override fun getErrorType() = ErrorType.DataFetchingException

  override fun getErrorCode() = ErrorCode.NOT_FOUND
}
