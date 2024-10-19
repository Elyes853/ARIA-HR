package com.itpeac.ariarh.middleoffice.web.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itpeac.ariarh.middleoffice.domain.Category;
import com.itpeac.ariarh.middleoffice.domain.Question;
import com.itpeac.ariarh.middleoffice.domain.Test;
import com.itpeac.ariarh.middleoffice.service.QuestionService;
import com.itpeac.ariarh.middleoffice.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController

public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/api/test")
    ResponseEntity<?> createCategory(@RequestBody Map<String, Object> payload){
        String nature = (String) payload.get("nature");
        ObjectMapper mapper = new ObjectMapper();
        List<Question> questionsList = mapper.convertValue(payload.get("questionsList"), new TypeReference<List<Question>>(){});
        try{
            System.out.println(nature);
            System.out.println(questionsList);


            Test newTest = new Test();
            newTest.setNature(nature);
            newTest.setQuestionsList(questionsList);
            newTest.setCreatedBy("system");
            newTest.setLastModifiedBy("system");
            newTest.setCreatedDate(Instant.now());
            newTest.setLastModifiedDate(Instant.now());

            Test savedTest = testService.saveTest(newTest);
            return ResponseEntity.ok(savedTest);
        } catch (Exception e){
            e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
