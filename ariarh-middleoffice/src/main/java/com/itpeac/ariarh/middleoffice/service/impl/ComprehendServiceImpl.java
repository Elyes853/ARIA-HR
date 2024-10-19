package com.itpeac.ariarh.middleoffice.service.impl;

import com.itpeac.ariarh.middleoffice.service.AWSClientService;
import com.itpeac.ariarh.middleoffice.service.ComprehendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.comprehend.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComprehendServiceImpl implements ComprehendService {

    private static final Logger log = LoggerFactory.getLogger(ComprehendServiceImpl.class);
    @Autowired
    private AWSClientService awsClientService;

    @Override
    public String detectAndCleanPII(String text) {
        DetectPiiEntitiesRequest detectPiiEntitiesRequest =
                DetectPiiEntitiesRequest.builder().text(text)
                        .languageCode(LanguageCode.EN)
                        .build();

        DetectPiiEntitiesResponse detectPiiEntitiesResponse = awsClientService.getAmazonComprehend()
                .detectPiiEntities(detectPiiEntitiesRequest);
        List<PiiEntity> piiEntities = detectPiiEntitiesResponse.entities();



        StringBuilder cleanedText = new StringBuilder(text);
        for (PiiEntity piiEntity : piiEntities){
            int start = piiEntity.beginOffset();
            int end = piiEntity.endOffset();
            String entity = text.substring(start, end);


            String entityType = piiEntity.type().toString();
            if (entityType.equals("NAME") || entityType.equals("PHONE") || entityType.equals("EMAIL")|| entityType.equals("URL")) {
            for (int i = start; i < end; i++){
                String c = "X";
                cleanedText.setCharAt(i, c.charAt(0));

            }
            }
        }

        log.info(cleanedText.toString());
        return cleanedText.toString();
    }
    @Override
    public Map<String, String> detectPersonalInfo(String text){
        DetectPiiEntitiesRequest detectPiiEntitiesRequest =
                DetectPiiEntitiesRequest.builder().text(text)
                        .languageCode(LanguageCode.EN)
                        .build();

        DetectPiiEntitiesResponse detectPiiEntitiesResponse = awsClientService.getAmazonComprehend()
                .detectPiiEntities(detectPiiEntitiesRequest);
        List<PiiEntity> piiEntities = detectPiiEntitiesResponse.entities();
        Map<String, String> piiEntityMap = new HashMap<>();

        for (PiiEntity piiEntity : piiEntities){
            int start = piiEntity.beginOffset();
            int end = piiEntity.endOffset();
            String entity = text.substring(start, end);

            String entityType = piiEntity.type().toString();
            if (entityType.equals("NAME") || entityType.equals("PHONE") || entityType.equals("EMAIL") || entityType.equals("ADDRESS") ) {
                piiEntityMap.put(entityType, entity);



            /*     if(piiEntityMap.containsKey(entityType)){
                    String existingEntity = piiEntityMap.get(entityType);
                    piiEntityMap.put(entityType, existingEntity + ", " + entity);
                } else {
                    piiEntityMap.put(entityType, entity);
                }*/




                }
            }

        System.out.println(piiEntityMap);
        return piiEntityMap;
    }
}
