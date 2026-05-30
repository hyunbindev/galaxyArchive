package com.hyunbindev.article.image.port.inbound

import java.util.UUID

interface ArticleImageManageUseCase {
    fun linkImageToArticle(articleId:Long, articleUuids:List<UUID>)
}