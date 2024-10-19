package com.itpeac.ariarh.middleoffice.service.impl;

import com.itpeac.ariarh.middleoffice.domain.Question;
import com.itpeac.ariarh.middleoffice.repository.jpa.QuestionRepository;
import com.itpeac.ariarh.middleoffice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question saveQuestion(Question question) { return questionRepository.save(question);}

    @Override
    public List<Question> getQuestions() { return questionRepository.findAll();}




}
