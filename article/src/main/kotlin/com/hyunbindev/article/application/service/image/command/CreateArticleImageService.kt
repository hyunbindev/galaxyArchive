package com.hyunbindev.article.application.service.image.command

import com.hyunbindev.article.domain.image.repository.ArticleImageRepository
import org.springframework.stereotype.Service

@Service
internal class CreateArticleImageService(
    private val articleImageRepository: ArticleImageRepository,
) {
    fun upLoadArticleImage() {

    }
}