package com.amp.news

import com.coxautodev.graphql.tools.SchemaParser
import com.amp.news.article.webservice.ArticleInput
import com.amp.news.article.webservice.ArticleMutationResolver
import com.amp.news.article.webservice.ArticleQueryResolver
import com.amp.news.article.webservice.ArticleResolver
import com.amp.news.comment.webservice.CommentMutationResolver
import com.amp.news.comment.webservice.CommentQueryResolver
import com.amp.news.comment.webservice.CommentResolver
import com.amp.news.core.webservice.ScalarDateTime
import com.amp.news.tag.webservice.TagQueryResolver
import com.amp.news.user.FeignConfig
import graphql.servlet.SimpleGraphQLHttpServlet
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(defaultConfiguration = [(FeignConfig::class)])
class NewsApplication : SpringBootServletInitializer() {

  @Bean
  fun servletRegistrationBean(
    articleMutationResolver: ArticleMutationResolver,
    articleQueryResolver: ArticleQueryResolver,
    commentMutationResolver: CommentMutationResolver,
    commentQueryResolver: CommentQueryResolver,
    tagQueryResolver: TagQueryResolver,
    articleResolver: ArticleResolver,
    commentResolver: CommentResolver
  ): ServletRegistrationBean<*> {
    val schema = SchemaParser.newParser()
      .scalars(ScalarDateTime())
      .dictionary(ArticleInput::class)
      .resolvers(
        articleMutationResolver,
        articleQueryResolver,
        commentMutationResolver,
        commentQueryResolver,
        tagQueryResolver,
        articleResolver,
        commentResolver
      )
      .file("news.graphqls").build().makeExecutableSchema()
    val servlet = SimpleGraphQLHttpServlet.newBuilder(schema).build()
    return ServletRegistrationBean(servlet, "/graphql")
  }

}

fun main(args: Array<String>) {
  SpringApplication.run(NewsApplication::class.java)
}
