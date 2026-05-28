package com.hyunbindev.article.image.data

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

class ArticleImageDto{
    @Schema(description = "request image upload")
    data class ImageUploadRequest(
        val originalName:String,
        val contentType:String,
        val rawKey:String?,
    )
    data class ImageUploadResponseDto(
        val imageRawKey: String,
        val imageUUID: UUID
    )
}