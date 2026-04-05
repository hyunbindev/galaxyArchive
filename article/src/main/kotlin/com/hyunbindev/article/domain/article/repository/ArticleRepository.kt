package com.hyunbindev.article.domain.article.repository

import com.hyunbindev.article.domain.article.ArticleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<ArticleEntity, Long> {

    @Query("SELECT a FROM ArticleEntity a WHERE a.id=:id AND a.isDeleted = false")
    fun findArticleById(id: Long): ArticleEntity?
}