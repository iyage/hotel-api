package com.api.config;

import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.api.exceptions.ResourceNotFoundExption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;
@Component
public class S3Util {
    private static final String BUCKET =  System.getenv("BUCKET");
    private String fileLocation ="";
    public String uploadFile(String fileName, InputStream inputStream)
            throws S3Exception, AwsServiceException, SdkClientException, IOException {
        S3Client client = S3Client.builder()
                .build();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key("file-upload/"+fileName)
                .acl("public-read")
                .build();
        client.putObject(request,RequestBody.fromInputStream(inputStream, inputStream.available()));

        S3Waiter waiter = client.waiter();
        HeadObjectRequest waitRequest = HeadObjectRequest.builder()
                .bucket(BUCKET)
                .key("file-upload/"+fileName)
                .build();

        WaiterResponse<HeadObjectResponse> waitResponse = waiter.waitUntilObjectExists(waitRequest);
        waitResponse.matched().response().ifPresent(x -> {
        fileLocation = BUCKET+".s3.amazonaws.com/file-upload/"+fileName;
        });

        if(fileLocation.equals("")) throw new ResourceNotFoundExption("Image not uploaded");
        else return   fileLocation;
    }



}
