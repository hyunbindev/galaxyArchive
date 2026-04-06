package com.hyunbindev.api.article.controller

import com.hyunbindev.article.application.port.CreateArticleImageUseCase
import com.hyunbindev.common.auth.LoginUserId
import com.hyunbindev.common.image.ImageUploadMetadata
import com.hyunbindev.common.image.RequestByteStream
import com.hyunbindev.common.image.UploadImageHeader
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.io.InputStream
import java.util.UUID

@RestController
@RequestMapping("/api/v1/articles/images")
class ArticleImageController(
    private val createArticleImageUseCase: CreateArticleImageUseCase
) {
    @PostMapping
    fun uploadArticleImage(
        @LoginUserId userId: UUID,
        @UploadImageHeader metadata: ImageUploadMetadata,
        @RequestByteStream imageStream: InputStream
    ){
        createArticleImageUseCase.upLoadArticleImage(userId,metadata,imageStream)
    }
}