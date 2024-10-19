package com.itpeac.ariarh.middleoffice.service.impl;

import com.itpeac.ariarh.middleoffice.domain.Test;
import com.itpeac.ariarh.middleoffice.repository.jpa.TestRepository;
import com.itpeac.ariarh.middleoffice.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestRepository testRespository;


    @Override
    public Test saveTest(Test test) {
        return testRespository.save(test);
    }
}
