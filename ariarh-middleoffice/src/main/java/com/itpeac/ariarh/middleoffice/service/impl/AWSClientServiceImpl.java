package com.itpeac.ariarh.middleoffice.service.impl;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.itpeac.ariarh.middleoffice.service.AWSClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.comprehend.ComprehendClient;
import software.amazon.awssdk.services.textract.TextractClient;

@Service
@Profile("!dev")
public class AWSClientServiceImpl implements AWSClientService {
    @Value("${cloud.aws.region.static}")
    private String region;
    @Override
    public AmazonS3 getAmazonS3Bucket() {
        return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region)).build();
    }

    @Override
    public TextractClient getAmazonTextract() {
        return TextractClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.EU_WEST_3)
                .build();
    }

    @Override
    public ComprehendClient getAmazonComprehend() {
        return ComprehendClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.EU_WEST_3)
                .build();
    }


}
