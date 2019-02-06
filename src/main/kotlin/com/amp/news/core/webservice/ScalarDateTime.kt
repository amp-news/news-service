package com.amp.news.core.webservice

import com.amp.news.core.util.parseDate
import com.amp.news.core.util.toDate
import com.amp.news.core.util.toISOString
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import org.springframework.stereotype.Component
import java.util.Date

@Component
class ScalarDateTime :
  GraphQLScalarType("DateTime", "DateTime type", object : Coercing<Date, String> {

    private fun convertImpl(input: Any?): Date? {
      if (input is String) {
        val localDateTime = parseDate(input)
        if (localDateTime != null) {
          return localDateTime.toDate()
        }
      }
      return null
    }

    override fun parseValue(input: Any?): Date {
      return convertImpl(input) ?: throw CoercingSerializeException("Invalid value $input for Date")
    }

    override fun parseLiteral(input: Any?) =
      if (input is StringValue) convertImpl(input.value) else null

    override fun serialize(input: Any?) =
      if (input is Date) {
        input.toISOString()
      } else {
        val result =
          convertImpl(input) ?: throw CoercingSerializeException("Invalid value $input for Date")
        result.toISOString()
      }

  })
