package com.itpeac.ariarh.middleoffice.service.impl;

import com.itpeac.ariarh.middleoffice.service.AWSClientService;
import com.itpeac.ariarh.middleoffice.service.ComprehendService;
import com.itpeac.ariarh.middleoffice.service.TextractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.textract.model.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TextractServiceImpl implements TextractService {

    @Autowired
    private AWSClientService awsClientService;

    @Autowired
    private ComprehendService comprehendService;

    @Value("${cloud.s3.bucket}")
    private String bucketName;

    private static final Logger log = LoggerFactory.getLogger(TextractServiceImpl.class);

    @Override
    public Object[] analyzeDocument(String sourceDoc) {
        try {
            S3Object s3Object = S3Object.builder()
                    .bucket(bucketName)
                    .name(sourceDoc)
                    .build();

            Document document = Document.builder()
                    .s3Object(s3Object)
                    .build();

            AnalyzeDocumentRequest analyzeDocumentRequest = AnalyzeDocumentRequest.builder()
                    .featureTypes(FeatureType.LAYOUT)
                    .document(document)
                    .build();

            AnalyzeDocumentResponse analyzeDocument = awsClientService.getAmazonTextract().analyzeDocument(analyzeDocumentRequest);
            List<Block> blocks = analyzeDocument.blocks();
            log.info("Blocks: {}", blocks);

            String extractedText = processBlocks(blocks);
            log.info("Extracted Text: {}", extractedText);
            Object[] comprehendResults = new Object[2];
            comprehendResults[0] = comprehendService.detectAndCleanPII(extractedText);
            comprehendResults[1] = comprehendService.detectPersonalInfo(extractedText);
            return comprehendResults;
        } catch (TextractException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private String processBlocks(List<Block> blocks) {
        StringBuilder textBuilder = new StringBuilder();
        Map<String, Block> blockMap = blocks.stream()
                .collect(Collectors.toMap(Block::id, Function.identity()));

        for (Block block : blocks) {
            if (block.blockTypeAsString().startsWith("LAYOUT")) {
                processLayoutBlock(block, textBuilder, blockMap);
            }
        }
        return textBuilder.toString();
    }

    private void processLayoutBlock(Block block, StringBuilder textBuilder, Map<String, Block> blockMap) {
        if (block.relationships() != null) {
            for (Relationship relationship : block.relationships()) {
                if (relationship.typeAsString().equals("CHILD")) {
                    for (String id : relationship.ids()) {
                        Block relatedBlock = blockMap.get(id);
                        if (relatedBlock != null && relatedBlock.blockTypeAsString().equals("LINE")) {
                            textBuilder.append(relatedBlock.text()).append(" ");
                        }
                    }
                }
            }
            textBuilder.append("\n");
        }
    }

}
