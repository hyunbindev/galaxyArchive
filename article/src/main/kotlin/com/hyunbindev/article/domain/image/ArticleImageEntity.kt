package com.hyunbindev.article.domain.image

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
class ArticleImageEntity(
    @Column(nullable = true)
    var rawKey: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(unique = true, nullable = false)
    val imageUuid: UUID = UUID.randomUUID()

    @Column
    var status:ImageStatus = ImageStatus.PENDING

    @Column(nullable = true)
    var articleId: Long? = null

    @Column(nullable = true)
    var optimizedKey: String? = null

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now()
}

enum class ImageStatus(var status: String) {
    PENDING("PENDING"),
    CONVERTING("CONVERTING"),
    CONVERTED("CONVERTED"),
    FAILED("FAILED"),
    READY("READY")
}