package com.itpeac.ariarh.middleoffice.service;

import com.itpeac.ariarh.middleoffice.domain.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {
    Question saveQuestion(Question question);

        public List<Question> getQuestions();
}
