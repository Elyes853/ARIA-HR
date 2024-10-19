package com.itpeac.ariarh.middleoffice.service.impl;

import com.itpeac.ariarh.middleoffice.service.UploadResponseService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UploadResponseServiceImpl implements UploadResponseService {
    private String message;
    private Map<String,String> data;


    @Override
    public String getMessage() {
        return message;
    }
    @Override
    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public Map<String,String> getData() {
        return data;
    }
    @Override
    public void setData(Map<String,String> data) {
        this.data = data;
    }
}
