package com.hyunbindev.article.comment.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = true)
    var parent: CommentEntity?=null
    ) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @Column(nullable = false, updatable = false)
    var created: LocalDateTime = LocalDateTime.now()
        private set

    @Column
    var isDeleted: Boolean = false
        private set

    fun delete(){
        this.isDeleted = true
    }
}