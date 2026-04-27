package com.hyunbindev.article.application.service.image.command

import com.hyunbindev.article.application.port.ArticleImageManageUseCase
import com.hyunbindev.article.domain.image.repository.ArticleImageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
internal class ArticleImageManageService(
    val articleImageRepository: ArticleImageRepository
): ArticleImageManageUseCase {
    @Transactional
    override fun linkImageToArticle(articleId:Long, articleUuids:List<UUID>){
        articleImageRepository.linkToArticles(articleId,articleUuids)
    }
}