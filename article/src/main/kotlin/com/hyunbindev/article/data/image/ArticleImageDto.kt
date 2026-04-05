package com.hyunbindev.article.data.image

import io.swagger.v3.oas.annotations.media.Schema
import java.io.InputStream

class ArticleImageDto{
    @Schema(description = "request image upload")
    data class ImageUploadRequest(
        val imageByteStream: InputStream,
    )
}
