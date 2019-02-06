package com.amp.news.core.webservice.exception

import graphql.ErrorType

class ValidationException(
  msg: String,
  private val extensions: Map<String, String> = emptyMap()
) : WebserviceException(msg) {

  override fun getErrorCode() = ErrorCode.VALIDATION_ERROR

  override fun getErrorType() = ErrorType.ValidationError

  override fun getExtensions() = extensions
}
