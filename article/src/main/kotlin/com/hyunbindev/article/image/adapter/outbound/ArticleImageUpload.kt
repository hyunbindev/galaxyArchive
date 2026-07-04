package com.hyunbindev.article.image.adapter.outbound

import java.io.InputStream

interface ArticleImageUpload {
    fun uploadImage(key:String, fileSize:Long, contentType:String, imageByteStream: InputStream):String
}