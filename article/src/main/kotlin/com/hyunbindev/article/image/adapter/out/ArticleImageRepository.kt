package com.hyunbindev.article.image.adapter.out

import com.hyunbindev.article.image.domain.ArticleImageEntity
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