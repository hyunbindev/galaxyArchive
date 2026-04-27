package com.hyunbindev.article.domain.image.repository

import com.hyunbindev.article.domain.image.entity.ArticleImageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface ArticleImageRepository : JpaRepository<ArticleImageEntity, Long> {
    fun findByImageUuid(imageUuid: UUID): ArticleImageEntity?


    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE ArticleImageEntity a 
        SET a.articleId = :articleId
        WHERE a.imageUuid IN :imageUuid
    """)
    fun linkToArticles(articleId:Long, imageUuid: List<UUID>):Int
}