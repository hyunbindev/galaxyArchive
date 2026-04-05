package com.hyunbindev.api.article.controller

import com.hyunbindev.api.article.composition.ArticleQueryComposition
import com.hyunbindev.api.article.data.ArticleCompositionDto
import com.hyunbindev.article.application.port.ArticleGraphUseCase
import com.hyunbindev.article.application.port.CreateArticleUseCase
import com.hyunbindev.article.data.article.ArticleDto
import com.hyunbindev.article.domain.vector.VectorEdge
import com.hyunbindev.common.auth.LoginUserId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/articles")
class ArticleController(
    private val createArticleUseCase: CreateArticleUseCase,
    private val articleGraphUseCase: ArticleGraphUseCase,
    private val articleQueryComposition: ArticleQueryComposition,
) {
    @PostMapping
    fun createArticle(@LoginUserId userId: UUID, @RequestBody req: ArticleDto.CreateRequest):Long{
        return createArticleUseCase.createArticle(userId, req)
    }

    @GetMapping("/graphs")
    fun getArticleGraph(): List<VectorEdge> {
        return articleGraphUseCase.getAllArticleGraph()
    }

    @GetMapping("/{articleId}")
    fun getArticle(@PathVariable articleId: Long): ArticleCompositionDto {
        return articleQueryComposition.getArticle(articleId)
    }
}