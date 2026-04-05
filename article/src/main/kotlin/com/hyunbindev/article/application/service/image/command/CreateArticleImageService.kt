package com.hyunbindev.article.application.service.image.command

import com.hyunbindev.article.data.image.ArticleImageDto
import com.hyunbindev.article.domain.image.entity.ArticleImageEntity
import com.hyunbindev.article.domain.image.port.ArticleImageUpload
import com.hyunbindev.article.domain.image.repository.ArticleImageRepository
import com.hyunbindev.article.exception.ArticleImageException
import com.hyunbindev.article.exception.constant.ArticleImageExceptionCode

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate
import java.io.File
import java.io.InputStream
import java.util.UUID

@Service
internal class CreateArticleImageService(
    private val articleImageRepository: ArticleImageRepository,
    private val articleImageUpload: ArticleImageUpload,
    private val transactionTemplate: TransactionTemplate
) {
    private val logger = LoggerFactory.getLogger(CreateArticleImageService::class.java)

    fun upLoadArticleImage(userId: UUID, request: ArticleImageDto.ImageUploadRequest, imageFile: File):String {
        val articleImageEntity: ArticleImageEntity = transactionTemplate.execute {
            articleImageRepository.save(ArticleImageEntity.startUpload())
        } ?: throw ArticleImageException(ArticleImageExceptionCode.ARTICLEIMAGE_TEMPTRASACTION_FAIL)

        return try{
            val rawKey:String = articleImageUpload.uploadImage(request.originalName, imageFile.length(),request.contentType, imageFile.inputStream())

            val managedEntity: ArticleImageEntity = transactionTemplate.execute {
                val managedEntity = articleImageRepository.findByImageUuid(articleImageEntity.imageUuid)

                managedEntity?.completeUpload(rawKey)

                managedEntity
            } ?: throw ArticleImageException(ArticleImageExceptionCode.ARTICLEIMAGE_INFO_LOST)

            managedEntity.imageUuid.toString()

        }catch (e: RuntimeException){
            logger.error("S3 Upload Failed for entity: ${articleImageEntity.imageUuid}", e)
            throw e;
        }
    }
}