package com.hyunbindev.article.image.adapter.inbound.command

import com.hyunbindev.article.image.port.inbound.ArticleImageManageUseCase
import com.hyunbindev.article.image.adapter.outbound.ArticleImageRepository
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