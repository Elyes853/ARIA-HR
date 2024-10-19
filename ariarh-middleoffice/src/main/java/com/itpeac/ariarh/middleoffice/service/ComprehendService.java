package com.itpeac.ariarh.middleoffice.service;

import com.amazonaws.services.comprehend.model.Entity;

import java.util.List;
import java.util.Map;


public interface ComprehendService {
    String detectAndCleanPII(String text);

    Map<String, String> detectPersonalInfo(String text);
}
