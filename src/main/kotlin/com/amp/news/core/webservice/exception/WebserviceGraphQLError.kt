package com.amp.news.core.webservice.exception

import graphql.ErrorType
import graphql.GraphQLError
import graphql.execution.ExecutionPath
import graphql.language.SourceLocation

class WebserviceGraphQLError(
  private val path: ExecutionPath,
  private val exception: WebserviceException,
  private val location: SourceLocation
) : GraphQLError {

  override fun getMessage(): String? = exception.message

  override fun getErrorType(): ErrorType = exception.getErrorType()

  override fun getLocations() = listOf(location)

  override fun getPath(): MutableList<Any> = path.toList()

  override fun getExtensions(): Map<String, Any> {
    val map = HashMap<String, Any>()
    map["errorCode"] = exception.getErrorCode()
    map.putAll(exception.getExtensions())
    return map
  }
}
