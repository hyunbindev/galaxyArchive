package com.hyunbindev.article.article.application.service.query

import com.hyunbindev.article.article.port.usecase.inbound.ArticleQueryUseCase
import com.hyunbindev.article.article.data.ArticleDto
import com.hyunbindev.article.article.adapter.outbound.ArticleRepository
import com.hyunbindev.article.article.adapter.outbound.ArticleSummary
import com.hyunbindev.article.article.data.ArticleSummaryDto
import com.hyunbindev.article.article.data.ArticleSummaryPageDto
import com.hyunbindev.article.article.port.usecase.inbound.ArticleStatsQueryUseCase
import com.hyunbindev.article.comment.port.inbound.ArticleCommentQueryUseCase
import com.hyunbindev.article.article.adapter.outbound.ArticleKeywordRepository
import com.hyunbindev.article.global.exception.ArticleException
import com.hyunbindev.article.global.exception.constant.ArticleExceptionCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
internal class ArticleQueryService(
    private val articleRepository: ArticleRepository,
    private val commentQueryUseCase: ArticleCommentQueryUseCase,
    private val articleKeywordRepository: ArticleKeywordRepository,

    ) : ArticleQueryUseCase, ArticleStatsQueryUseCase {

    override fun getArticle(id: Long): ArticleDto.Response {
        val article = articleRepository.findArticleById(id)
            ?: throw ArticleException(ArticleExceptionCode.ARTICLE_NOT_FOUND)

        val keywords = articleKeywordRepository.findAllByArticleOrderBySimilarityDesc(article)

        return ArticleDto.Response.from(article, keywords)
    }

    override fun getArticleSummaryPageByCursorAndAuthor(
        authorId: UUID,
        cursorArticleId: Long?,
        size: Int
    ): ArticleSummaryPageDto {
        val articleSummary: List<ArticleSummary> = articleRepository
            .findByArticleSummaryByUserIdByCursor(
                authorId = authorId,
                cursorId = cursorArticleId,
                size = size + 1,
                textLength = 100
            )

        val commentsCountMap: Map<Long, Int> = commentQueryUseCase
            .getCommentCountByArticleIds(articleSummary.map { it.id })

        val hasNextPage = articleSummary.size > size

        val articleIds = articleSummary.map { it.id }

        val keywordEntities = articleKeywordRepository.findAllByArticleIdInOrderBySimilarityDesc(articleIds)

        val keywordMap: Map<Long, List<String>> =
            keywordEntities.groupBy(keySelector = { it.article.id!! }, valueTransform = { it.keyword })

        val articleSummaryDtoList = articleSummary.take(size)
            .map { ArticleSummaryDto.from(it, commentsCountMap[it.id], keywordMap[it.id] ?: emptyList()) }

        return ArticleSummaryPageDto(
            articles = articleSummaryDtoList,
            size = articleSummaryDtoList.size,
            hasNextPage = hasNextPage,
            cursorArticleId = articleSummaryDtoList.lastOrNull()?.id
        )
    }

    override fun getArticleSummaryByIds(articleIds: List<Long>): List<ArticleSummaryDto> {

        val articleSummary: List<ArticleSummary> = articleRepository
            .findArticleByIdWithDeleted(articleIds, 100)

        val keywordEntities = articleKeywordRepository.findAllByArticleIdInOrderBySimilarityDesc(articleIds)

        val keywordMap: Map<Long, List<String>> =
            keywordEntities.groupBy(keySelector = { it.article.id!! }, valueTransform = { it.keyword })

        val commentsCountMap: Map<Long, Int> = commentQueryUseCase
            .getCommentCountByArticleIds(articleSummary.map { it.id })

        return articleSummary
            .map {
                ArticleSummaryDto.from(
                    it,
                    commentsCountMap[it.id],
                    keywordMap[it.id] ?: emptyList()
                )
            }
    }

    override fun getArticleCountByAuthorId(authorId: UUID): Int = articleRepository.countByAuthorId(authorId)

    override fun isArticleExist(id: Long): Boolean = articleRepository.existsById(id)
}