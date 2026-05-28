package com.hyunbindev.article.image.port.`in`

import com.hyunbindev.article.image.data.ArticleImageDto
import com.hyunbindev.common.image.ImageUploadMetadata
import java.io.InputStream
import java.util.UUID

interface CreateArticleImageUseCase {
    fun upLoadArticleImage(userId: UUID, request: ImageUploadMetadata, imageStream: InputStream):ArticleImageDto.ImageUploadResponseDto
}