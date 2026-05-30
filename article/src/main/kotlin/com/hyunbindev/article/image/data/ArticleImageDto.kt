package com.hyunbindev.article.image.data
import java.util.UUID

class ArticleImageDto{
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