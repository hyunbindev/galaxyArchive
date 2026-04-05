package com.hyunbindev.article.application.service.article.query

import com.hyunbindev.article.application.port.ArticleQueryUseCase
import com.hyunbindev.article.data.article.ArticleDto
import com.hyunbindev.article.domain.article.repository.ArticleRepository
import com.hyunbindev.article.exception.ArticleException
import com.hyunbindev.article.exception.constant.ArticleExceptionCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class ArticleQueryService(
    private val articleRepository: ArticleRepository,
): ArticleQueryUseCase {
    @Transactional(readOnly = true)
    override fun getArticle(id:Long): ArticleDto.Response{
        val article = articleRepository.findArticleById(id)?:
            throw ArticleException(ArticleExceptionCode.ARTICLE_NOT_FOUND)

        return ArticleDto.Response.from(article)
    }
}