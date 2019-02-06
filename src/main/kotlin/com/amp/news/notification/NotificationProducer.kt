package com.amp.news.notification

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class NotificationProducer {

    @Autowired
    private val rabbitTemplate: RabbitTemplate? = null

    @Autowired
    private val mapper: ObjectMapper? = null

    fun sendEvent(`object`: Any) {
        val objectToSend = mapper?.writeValueAsString(`object`)
        rabbitTemplate?.convertAndSend("events-exchange", "news.published", objectToSend)

    }
}
