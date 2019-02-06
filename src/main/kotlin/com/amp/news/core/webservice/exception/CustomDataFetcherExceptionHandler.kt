package com.amp.news.core.webservice.exception

import graphql.ExceptionWhileDataFetching
import graphql.execution.DataFetcherExceptionHandler
import graphql.execution.DataFetcherExceptionHandlerParameters

class CustomDataFetcherExceptionHandler : DataFetcherExceptionHandler {

  override fun accept(handlerParameters: DataFetcherExceptionHandlerParameters) {
    val exception = handlerParameters.exception
    val sourceLocation = handlerParameters.field.sourceLocation
    val path = handlerParameters.path

    val error = if (exception is WebserviceException) {
      WebserviceGraphQLError(path, exception, sourceLocation)
    } else {
      ExceptionWhileDataFetching(path, exception, sourceLocation)
    }
    handlerParameters.executionContext.addError(error, handlerParameters.path)
  }
}
