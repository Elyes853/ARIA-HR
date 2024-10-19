package com.itpeac.ariarh.middleoffice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.configurationprocessor.json.JSONException;

public interface ScoreAndResumeService {
    String score(String Job, String Resume) throws JsonProcessingException, JSONException;
    String resume(String Resume) throws JsonProcessingException, JSONException;
}
