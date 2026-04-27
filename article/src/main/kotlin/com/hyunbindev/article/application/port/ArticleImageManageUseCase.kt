package com.hyunbindev.article.application.port

import java.util.UUID

interface ArticleImageManageUseCase {
    fun linkImageToArticle(articleId:Long, articleUuids:List<UUID>)
}