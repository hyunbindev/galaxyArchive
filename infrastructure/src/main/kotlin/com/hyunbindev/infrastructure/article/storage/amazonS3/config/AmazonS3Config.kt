package com.hyunbindev.infrastructure.article.storage.amazonS3.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmazonS3Config(
    @param:Value("\${cloud.aws.credentials.access-key}") private val accessKey: String,
    @param:Value("\${cloud.aws.credentials.secret-key}") private val secretKey: String,
    @param:Value("\${cloud.aws.region.static}") private val region: String,
    @param:Value("\${cloud.aws.s3.endpoint}") private val endpoint: String,
    @param:Value("\${cloud.aws.s3.bucket}") private val bucket: String,
) {
    private val logger: Logger = LoggerFactory.getLogger(AmazonS3Config::class.java)
    @Bean
    fun amazonS3(): AmazonS3 {
        val credentials = BasicAWSCredentials(accessKey, secretKey)
        val s3Client = AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(endpoint, region))
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .enablePathStyleAccess()
            .build()

        try {
            if(!s3Client.doesBucketExistV2(bucket)){
                s3Client.createBucket(bucket)
                logger.info("Created bucket $bucket")
            }
            val publicReadPolicy = """
                {
                    "Version": "2012-10-17",
                    "Statement": [
                        {
                            "Sid": "PublicRead",
                            "Effect": "Allow",
                            "Principal": "*",
                            "Action": ["s3:GetObject"],
                            "Resource": ["arn:aws:s3:::$bucket/*"]
                        }
                    ]
                }
            """.trimIndent()

            s3Client.setBucketPolicy(bucket, publicReadPolicy)
            logger.info("Successfully set public read policy for bucket $bucket")
        }catch(e:Exception){
            logger.error("Error can not init Amazon S3 Bucket $bucket", e)
            throw e
        }

        return s3Client
    }
}