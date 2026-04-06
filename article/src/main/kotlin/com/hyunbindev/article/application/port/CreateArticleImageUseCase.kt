package com.hyunbindev.article.application.port

import com.hyunbindev.article.data.image.ArticleImageDto
import com.hyunbindev.common.image.ImageUploadMetadata
import java.io.File
import java.io.InputStream
import java.util.UUID

interface CreateArticleImageUseCase {
    fun upLoadArticleImage(userId: UUID, request: ImageUploadMetadata, imageStream: InputStream):String
}