package com.hyunbindev.article.domain.repository

import com.hyunbindev.article.domain.entity.ArticleImageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleImageRepository :JpaRepository<ArticleImageEntity,Long> {
}