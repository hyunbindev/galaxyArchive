package com.hyunbindev.article.image.port.`in`

import java.util.UUID

interface ArticleImageManageUseCase {
    fun linkImageToArticle(articleId:Long, articleUuids:List<UUID>)
}