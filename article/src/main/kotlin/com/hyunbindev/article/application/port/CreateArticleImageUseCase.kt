package com.hyunbindev.article.application.port

import com.hyunbindev.article.data.image.ArticleImageDto
import java.io.File
import java.util.UUID

interface CreateArticleImageUseCase {
    fun upLoadArticleImage(userId: UUID, request: ArticleImageDto.ImageUploadRequest, imageFile: File):String
}