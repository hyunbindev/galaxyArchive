package com.hyunbindev.article.domain.vector.entity

import com.hyunbindev.article.domain.article.entity.ArticleEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
class ArticleVectorEntity(
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    val article: ArticleEntity,

    @Column(columnDefinition = "vector(1024)")
    @JdbcTypeCode(SqlTypes.ARRAY)
    val vector: FloatArray,
) {
    @Id
    @Column(name = "article_id")
    val articleId: Long? = null

    @LastModifiedDate
    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
}