package com.amp.news.user

import feign.Contract
import feign.Logger
import feign.codec.Encoder
import feign.jackson.JacksonEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfig {

    @Bean
    fun feignLogger(): Logger.Level {
        return Logger.Level.FULL
    }

    @Bean
    fun feignContract(): Contract {
        return Contract.Default()
    }

    @Bean
    fun feignEncoder(): Encoder {
        return JacksonEncoder()
    }
}
