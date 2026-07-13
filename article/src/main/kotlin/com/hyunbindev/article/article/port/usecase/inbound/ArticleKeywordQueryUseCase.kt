package com.hyunbindev.article.article.port.usecase.inbound

import com.hyunbindev.article.article.data.ArticleKeywordDto

interface ArticleKeywordQueryUseCase {
    fun getKeywordsByArticleIds(articleIds:List<Long>,limit:Int=5):List<ArticleKeywordDto>
}