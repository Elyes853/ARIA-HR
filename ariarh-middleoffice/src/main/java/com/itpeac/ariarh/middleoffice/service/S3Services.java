package com.itpeac.ariarh.middleoffice.service;

import com.itpeac.ariarh.middleoffice.domain.CV;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import java.util.List;
import java.util.Map;

public interface S3Services {
    List<String> listObjectsInBucket(String prefix);

    Map<String, String> uploadFile(MultipartFile file);

    Long saveCV(String path, String eTag, String body);

    CV getCVById(Long id);

    Resource getObject(String Link);

    String determineContentType(String path);
}
