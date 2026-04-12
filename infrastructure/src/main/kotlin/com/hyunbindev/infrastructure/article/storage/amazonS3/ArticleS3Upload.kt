package com.hyunbindev.infrastructure.article.storage.amazonS3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.hyunbindev.article.domain.image.port.ArticleImageUpload
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class ArticleS3Upload(
    private val amazonS3: AmazonS3,
    @param:Value("\${cloud.aws.s3.bucket}") private val bucket: String,
): ArticleImageUpload {

    private val prefix: String = "article/origin"
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun uploadImage(key: String,fileSize: Long,contentType: String,imageByteStream: InputStream): String {

        //image metadata length contentType
        val metadata = ObjectMetadata().apply {
            contentLength = fileSize
            setContentType(contentType)
        }

        // try-with-resources for input stream
        imageByteStream.use { stream ->
            return try {

                val fullPath = "$prefix/$key"

                val putRequest = PutObjectRequest(bucket, fullPath, stream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead)

                amazonS3.putObject(putRequest)

                fullPath
            }catch (e: Exception) {
                logger.error("Error uploading image", e)
                throw e
            }
        }
    }
}