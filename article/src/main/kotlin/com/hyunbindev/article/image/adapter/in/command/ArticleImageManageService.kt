package com.hyunbindev.article.image.adapter.`in`.command

import com.hyunbindev.article.image.port.`in`.ArticleImageManageUseCase
import com.hyunbindev.article.image.adapter.out.ArticleImageRepository
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