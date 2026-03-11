package com.hyunbindev.api.controller.article

import com.hyunbindev.article.application.port.ArticleGraphUserCase
import com.hyunbindev.article.application.port.CreateArticleUseCase
import com.hyunbindev.article.data.dto.ArticleDto
import com.hyunbindev.common.auth.LoginUserId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/article")
class ArticleController(
    private val createArticleUseCase: CreateArticleUseCase,
    private val articleGraphUserCase: ArticleGraphUserCase
) {
    @PostMapping
    fun createArticle(@LoginUserId userId: UUID, @RequestBody req: ArticleDto.CreateRequest){
        createArticleUseCase.createArticle(userId, req)
    }
    @GetMapping("/graph")
    fun getArticleGraph() = articleGraphUserCase.getAllArticleGraph()
}