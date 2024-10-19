package com.itpeac.ariarh.middleoffice.service.impl;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.itpeac.ariarh.middleoffice.domain.CV;
import com.itpeac.ariarh.middleoffice.repository.jpa.CVRepository;
import com.itpeac.ariarh.middleoffice.service.AWSClientService;
import com.itpeac.ariarh.middleoffice.service.S3Services;
import com.itpeac.ariarh.middleoffice.service.TextractService;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class S3ServiceImpl implements S3Services {
    private static final Logger log = LoggerFactory.getLogger(S3ServiceImpl.class);
 /*   @Autowired
    private CV;*/
     @Autowired
     private CVRepository cvRepository;

    @Autowired
    private AWSClientService awsClientService;

    @Autowired
    private TextractService textractService;

    @Value("${cloud.s3.bucket}")
    private String bucketName;
    private String eTag;
    private String s3Url;
    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public String getS3Url() {
        return s3Url;
    }

    public void setS3Url(String s3Url) {
        this.s3Url = s3Url;
    }


    @Override
    public List<String> listObjectsInBucket(String prefix) {
        AmazonS3 s3Client = awsClientService.getAmazonS3Bucket();
        ObjectListing objectListing = s3Client.listObjects(bucketName, prefix);
        List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        List<String> objectKeys = new ArrayList<>();

        for (S3ObjectSummary objectSummary : objectSummaries) {
            objectKeys.add(objectSummary.getKey());
        }

        return objectKeys;
    }

    @Override
    public Map<String, String> uploadFile(MultipartFile file) {
        try {
            AmazonS3 s3Client = awsClientService.getAmazonS3Bucket();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            InputStream inputStream = file.getInputStream();
            PutObjectResult result;
            Object[] analysisResult;
            if (inputStream != null) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                ByteBuffer byteBuffer = ByteBuffer.wrap(outputStream.toByteArray());

                // Convert ByteBuffer to InputStream
                InputStream byteBufferInputStream = new ByteArrayInputStream(byteBuffer.array());

                // Upload file to S3
                result = s3Client.putObject(bucketName, file.getOriginalFilename(), byteBufferInputStream, metadata);

                String path = String.valueOf(s3Client.getUrl(bucketName, file.getOriginalFilename()));
                System.out.println("File uploaded: " + file.getOriginalFilename());

                // Invoke Textract for the uploaded file
                analysisResult = textractService.analyzeDocument(file.getOriginalFilename());
                String analysedText = analysisResult[0].toString();
                Map<String, String> userInfo = (Map<String, String>) analysisResult[1];

                //put in database here
                Long cvId= saveCV(path, result.getETag(),analysedText);
                userInfo.put("cvId" , String.valueOf(cvId));
                return (userInfo);

            } else {
                throw new RuntimeException("Input stream is null");
            }
        } catch (IOException e) {

            System.out.println("IOException occurred while uploading file: " + e.getMessage());
            throw new RuntimeException("Failed to upload file: " + e.getMessage());

        }
    }

    @Override
    public Long saveCV(String path, String eTag, String body) {
        CV cv = new CV();

        cv.setPath(path);
        cv.seteTag(eTag);
        cv.setBody(body);

        CV savedCV = cvRepository.save(cv);

        return savedCV.getId();
    }

    @Override
    public CV getCVById(Long id){
        return cvRepository.getById(id);
    }

    @Override
    public Resource getObject(String path){
        try {
            AmazonS3 s3Client = awsClientService.getAmazonS3Bucket();
            URL url = new URL(path);

    /*        // Extract the bucket name from the host
            String bucketName = url.getHost();*/

            // Extract the file name from the path
            String keyName = url.getPath();

            keyName = URLDecoder.decode(keyName, StandardCharsets.UTF_8.name());


            // Remove the leading '/' from the path
            if (keyName.startsWith("/")) {
                keyName = keyName.substring(1);
            }

            S3Object s3Object = s3Client.getObject(bucketName, keyName);
            S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();

            return new InputStreamResource(s3ObjectInputStream);

        }catch (Exception e){
            e.getMessage();
            return null;
        }
    }

    @Override
    public String determineContentType(String path) {
        if (path.endsWith(".pdf")) {
            return "application/pdf";
        } else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (path.endsWith(".png")) {
            return "image/png";
        } else {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
    }



}
