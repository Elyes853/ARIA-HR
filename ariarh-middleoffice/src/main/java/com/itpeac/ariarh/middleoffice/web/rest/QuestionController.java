package com.itpeac.ariarh.middleoffice.web.rest;

import com.itpeac.ariarh.middleoffice.domain.Category;
import com.itpeac.ariarh.middleoffice.domain.Question;
import com.itpeac.ariarh.middleoffice.service.CategoryService;
import com.itpeac.ariarh.middleoffice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CategoryService categoryService;
    @GetMapping()
    public ResponseEntity<?> getQuestions(){
        try{
            List<Question> questionsList = questionService.getQuestions();
            return ResponseEntity.ok(questionsList);
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping()
    ResponseEntity<?> createCategory(@RequestBody Map<String, Object> payload){
        String question = (String) payload.get("question");
        Long categoryId = Long.valueOf((Integer) payload.get("category_id"));

        System.out.println(question);
        System.out.println(categoryId);

        try {
            Category category = categoryService.getCategoryById(categoryId);
            if (category == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category not found");
            }

            Question newQuestion = new Question();
            newQuestion.setQuestion(question);
            newQuestion.setCategoryId(category);
            newQuestion.setCreatedBy("system");
            newQuestion.setLastModifiedBy("system");
            newQuestion.setCreatedDate(Instant.now());
            newQuestion.setLastModifiedDate(Instant.now());

            Question savedQuestion = questionService.saveQuestion(newQuestion);
            return ResponseEntity.ok(savedQuestion);
        } catch (Exception e){
            e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
