package com.hyunbindev.article.domain.image.port

import com.hyunbindev.article.data.image.ArticleImageDto
import java.io.InputStream
import java.util.UUID

interface ArticleImageUpload {
    fun uploadImage(originalName:String, contentType:String, imageByteStream: InputStream):String
}