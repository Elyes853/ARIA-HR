package com.itpeac.ariarh.middleoffice.service;


import com.amazonaws.services.s3.AmazonS3;
import software.amazon.awssdk.services.comprehend.ComprehendClient;
import software.amazon.awssdk.services.textract.TextractClient;

public interface AWSClientService {
    AmazonS3 getAmazonS3Bucket();
    TextractClient getAmazonTextract();
    ComprehendClient getAmazonComprehend();


}