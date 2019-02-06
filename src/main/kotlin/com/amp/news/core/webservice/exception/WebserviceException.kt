package com.amp.news.core.webservice.exception

import graphql.ErrorType

abstract class WebserviceException(msg: String) : Exception(msg) {

  abstract fun getErrorType(): ErrorType

  open fun getExtensions(): Map<String, Any> = emptyMap()

  abstract fun getErrorCode(): ErrorCode
}
