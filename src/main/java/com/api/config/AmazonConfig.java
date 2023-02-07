package com.api.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

    @Bean
    public AmazonS3 s3() {
        System.out.println(System.getenv("ACCESSKEY"));
        AWSCredentials credentials = new BasicAWSCredentials(System.getenv("ACCESSKEY"),System.getenv("SECRETKEY"));
        return AmazonS3ClientBuilder
                .standard()
                .withRegion("us-east-1")
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

}
