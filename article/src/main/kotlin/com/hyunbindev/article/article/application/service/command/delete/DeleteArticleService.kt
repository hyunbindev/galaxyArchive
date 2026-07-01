package com.hyunbindev.article.article.application.service.command.delete

import com.hyunbindev.article.article.port.usecase.inbound.DeleteArticleUseCase
import com.hyunbindev.article.article.domain.ArticleEntity
import com.hyunbindev.article.article.adapter.outbound.ArticleRepository
import com.hyunbindev.article.global.exception.ArticleException
import com.hyunbindev.article.global.exception.constant.ArticleExceptionCode
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID

@Service
internal class DeleteArticleService(
    private val articleRepository: ArticleRepository,
): DeleteArticleUseCase {

    @Transactional
    override fun deleteArticle(userId: UUID, articleId: Long, articleTitle: String) {
        val article: ArticleEntity = articleRepository.findArticleById(articleId)
            ?: throw ArticleException(ArticleExceptionCode.ARTICLE_NOT_FOUND)

        article.takeIf { it.authorId == userId }
            ?: throw ArticleException(ArticleExceptionCode.ARTICLE_UNAUTHORIZED)

        article.takeIf { it.title == articleTitle }
            ?: throw ArticleException(ArticleExceptionCode.ARTICLE_FORBIDDEN, "입력한 제목과 게시글의 제목이 일치하지 않습니다.")

        article.delete()
    }
}