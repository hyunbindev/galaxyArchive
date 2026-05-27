package com.hyunbindev.article.domain.comment

import com.hyunbindev.article.domain.article.entity.ArticleEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
class CommentEntity(
    @Column(columnDefinition="TEXT", nullable = false)
    val text:String,

    @Column
    val authorId: UUID,

    @Column(nullable = false)
    val articleId: Long,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = true)
    var parent: CommentEntity?=null

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var created: LocalDateTime = LocalDateTime.now()

    @Column
    var isDeleted: Boolean = false
        private set

    fun delete(){
        this.isDeleted = true
    }
}