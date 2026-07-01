package com.hyunbindev.api.article.controller

import com.hyunbindev.api.article.composition.ArticleQueryComposition
import com.hyunbindev.api.article.data.ArticleCompositionResponse
import com.hyunbindev.article.embedding.port.usecase.inbound.ArticleGraphUseCase
import com.hyunbindev.article.article.port.usecase.inbound.CreateArticleUseCase
import com.hyunbindev.article.article.port.usecase.inbound.DeleteArticleUseCase
import com.hyunbindev.article.article.data.ArticleDto
import com.hyunbindev.article.embedding.data.ArticleGraphDto
import com.hyunbindev.common.auth.LoginUserId
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/articles")
class ArticleController(
    private val createArticleUseCase: CreateArticleUseCase,
    private val articleGraphUseCase: ArticleGraphUseCase,
    private val articleQueryComposition: ArticleQueryComposition,
    private val articleDeleteUseCase: DeleteArticleUseCase,
) {
    @PostMapping
    fun createArticle(@LoginUserId userId: UUID, @RequestBody req: ArticleDto.CreateRequest):Long{
        return createArticleUseCase.createArticle(userId, req)
    }

    @GetMapping("/graphs")
    fun getArticleGraph(): ArticleGraphDto {
        return articleGraphUseCase.getAllArticleGraph()
    }

    @GetMapping("/{articleId}")
    fun getArticle(@PathVariable articleId: Long): ArticleCompositionResponse {
        return articleQueryComposition.getArticle(articleId)
    }

    @DeleteMapping("/{articleId}")
    fun deleteArticle(@LoginUserId userId:UUID, @PathVariable articleId: Long, @RequestParam articleTitle:String) {
        articleDeleteUseCase.deleteArticle(userId, articleId, articleTitle)
    }
}