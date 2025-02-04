package com.dobby.backend.infrastructure.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.dobby.backend.infrastructure.config.properties.S3Properties
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@RequiredArgsConstructor
class S3Config {

    private val properties: S3Properties

    @Autowired
    constructor(properties: S3Properties) {
        this.properties = properties
    }

    @Bean
    fun amazonS3Client(): AmazonS3? {
        val awsCredentialsProvider = BasicAWSCredentials(properties.credentials.accessKey, properties.credentials.secretKey)

        return AmazonS3ClientBuilder.standard()
            .withRegion(properties.region.static)
            .withCredentials(AWSStaticCredentialsProvider(awsCredentialsProvider))
            .build()
    }
}
