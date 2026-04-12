package com.hyunbindev.article.application.service.image.command

import com.hyunbindev.article.application.port.CreateArticleImageUseCase
import com.hyunbindev.article.data.image.ArticleImageDto
import com.hyunbindev.article.domain.image.entity.ArticleImageEntity
import com.hyunbindev.article.domain.image.port.ArticleImageUpload
import com.hyunbindev.article.domain.image.repository.ArticleImageRepository
import com.hyunbindev.article.exception.ArticleImageException
import com.hyunbindev.article.exception.constant.ArticleImageExceptionCode
import com.hyunbindev.common.image.ImageExtension
import com.hyunbindev.common.image.ImageUploadMetadata

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
): CreateArticleImageUseCase {
    private val logger = LoggerFactory.getLogger(CreateArticleImageService::class.java)

    override fun upLoadArticleImage(userId: UUID, request: ImageUploadMetadata, imageStream: InputStream): String {


        val articleImageEntity: ArticleImageEntity = transactionTemplate.execute {

            articleImageRepository.save(ArticleImageEntity.startUpload(userId))

        } ?: throw ArticleImageException(ArticleImageExceptionCode.ARTICLEIMAGE_TEMPTRASACTION_FAIL)

        return try{

            //image key it contained prefix path and image uuid key
            val rawKey:String = articleImageUpload.uploadImage( articleImageEntity.imageUuid.toString(), request.contentLength,request.getImageExtension(), imageStream)

            val managedEntity: ArticleImageEntity = transactionTemplate.execute {
                val managedEntity = articleImageRepository.findByImageUuid(articleImageEntity.imageUuid)

                //Change image status after uploaded
                managedEntity?.completeUpload(rawKey)

                managedEntity
            } ?: throw ArticleImageException(ArticleImageExceptionCode.ARTICLEIMAGE_INFO_LOST)

            managedEntity.rawKey ?: throw RuntimeException("failed to load raw key")

        }catch (e: RuntimeException){
            logger.error("S3 Upload Failed for entity: ${articleImageEntity.imageUuid}", e)
            throw e;
        }
    }
}