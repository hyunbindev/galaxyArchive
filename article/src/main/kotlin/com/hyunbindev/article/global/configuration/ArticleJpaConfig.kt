package com.hyunbindev.article.global.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages =
    ["com.hyunbindev.article.article.domain",
     "com.hyunbindev.article.embedding.domain",
     "com.hyunbindev.article.image.domain",
     "com.hyunbindev.article.comment.domain",
    ])
@EnableJpaRepositories(basePackages =
    ["com.hyunbindev.article.article.adapter.out",
     "com.hyunbindev.article.embedding.adapter.out",
     "com.hyunbindev.article.image.adapter.out",
     "com.hyunbindev.article.comment.adapter.out",
    ])
class ArticleJpaConfig