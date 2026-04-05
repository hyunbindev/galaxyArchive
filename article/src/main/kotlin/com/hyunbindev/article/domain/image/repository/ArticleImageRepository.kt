package com.hyunbindev.article.domain.image.repository

import com.hyunbindev.article.domain.image.ArticleImageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleImageRepository : JpaRepository<ArticleImageEntity, Long> {
}