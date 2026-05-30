package com.hyunbindev.article.article.domain

import com.hyunbindev.article.article.data.ArticleDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.UUID

@Entity
@EntityListeners(AuditingEntityListener::class)
class ArticleEntity(
    @Column(nullable = false)
    val title:String,

    @Column(nullable = false)
    val authorId: UUID,

    @Column(columnDefinition = "TEXT", nullable = false)
    val text:String,

    @Column(columnDefinition = "TEXT", nullable = false)
    val rawText:String,

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null

    @CreatedDate
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    var isDeleted: Boolean = false

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: ArticleStatus = ArticleStatus.PENDING

    companion object{
        fun from(authorId: UUID, req: ArticleDto.CreateRequest):ArticleEntity{
            return ArticleEntity(
                title = req.title,
                authorId = authorId,
                text = req.text,
                rawText = req.rawText
            )
        }
    }

    fun delete(){
        isDeleted = true
    }
}