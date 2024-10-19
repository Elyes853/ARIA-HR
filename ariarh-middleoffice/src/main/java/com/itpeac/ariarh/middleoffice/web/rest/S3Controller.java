package com.itpeac.ariarh.middleoffice.web.rest;
import com.itpeac.ariarh.middleoffice.service.ComprehendService;
import com.itpeac.ariarh.middleoffice.service.S3Services;
import com.itpeac.ariarh.middleoffice.service.UploadResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class S3Controller {
    @Autowired
    private ComprehendService comprehendService;

    @Autowired
    private S3Services s3Services;

    @Autowired
    private UploadResponseService uploadResponse;

    @GetMapping("/listObjects")
    public List<String> listObjectsInBucket(@RequestParam String prefix) {
        // Call the S3Services method to list objects
        return s3Services.listObjectsInBucket(prefix);
    }

    @PostMapping("/upload")
    public ResponseEntity<UploadResponseService> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            uploadResponse.setMessage("File Uploaded");

            Map<String, String> userInfo = s3Services.uploadFile(file);
            uploadResponse.setData(userInfo);


            //content and path to S3
            return new ResponseEntity<>(uploadResponse, HttpStatus.OK);
        } catch (Exception e) {
            uploadResponse.setMessage("Faield to upload file");
            return new ResponseEntity<>(uploadResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
