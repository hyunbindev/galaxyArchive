package com.hyunbindev.article.domain.image.repository

import com.hyunbindev.article.domain.image.entity.ArticleImageEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ArticleImageRepository : JpaRepository<ArticleImageEntity, Long> {
    fun findByImageUuid(imageUuid: UUID): ArticleImageEntity?
}