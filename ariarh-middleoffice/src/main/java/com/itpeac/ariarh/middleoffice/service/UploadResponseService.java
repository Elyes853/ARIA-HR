package com.itpeac.ariarh.middleoffice.service;

import java.util.Map;

public interface UploadResponseService {
    String getMessage();

    void setMessage(String s);

    Map<String,String> getData();

    void setData(Map<String, String> data);
}
