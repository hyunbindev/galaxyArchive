package com.hyunbindev.article.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = ["com.hyunbindev.article.domain"])
@EnableJpaRepositories(basePackages = ["com.hyunbindev.article.domain"])
class ArticleJpaRepository {
}