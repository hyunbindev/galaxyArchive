package com.hyunbindev.article.article.adapter.`in`.query

import com.hyunbindev.article.article.port.`in`.ArticleQueryUseCase
import com.hyunbindev.article.article.data.ArticleDto
import com.hyunbindev.article.article.adapter.out.ArticleRepository
import com.hyunbindev.article.global.exception.ArticleException
import com.hyunbindev.article.global.exception.constant.ArticleExceptionCode
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