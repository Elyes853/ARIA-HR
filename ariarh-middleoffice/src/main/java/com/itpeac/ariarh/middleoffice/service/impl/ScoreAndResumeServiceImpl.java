package com.itpeac.ariarh.middleoffice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itpeac.ariarh.middleoffice.domain.ChatgptRequest;
import com.itpeac.ariarh.middleoffice.domain.Message;
import com.itpeac.ariarh.middleoffice.service.ConversationService;
import com.itpeac.ariarh.middleoffice.service.ScoreAndResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Service
public class ScoreAndResumeServiceImpl implements ScoreAndResumeService {

    @Value("${chatgpt.model}")
    private String model;

    @Value("${chatgpt.api.url}")
    private String apiUrl;

    @Value("${chatgpt.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate;

    @Autowired
    ConversationService conversationService;
    @Autowired
    public ScoreAndResumeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String score(String job, String resume) throws JsonProcessingException, JSONException {
        List<Message> messageList = new ArrayList<Message>();

        ChatgptRequest request = new ChatgptRequest(model, messageList);

        String instructions = "Given the job description and the candidate's resume in any language, generate only a matching score and only in percentage (X%), that reflects the suitability of the candidate for the job. Consider the following factors:\n" +
                "\n" +
                "1. **Relevance of Skills**: Compare the skills required for the job with the skills listed in the candidate's resume. The more overlap there is, the higher the score should be.\n" +
                "\n" +
                "2. **Work Experience**: Consider the candidate's previous work experience. If the candidate has worked in similar roles or industries before, this should increase the matching score.\n" +
                "\n" +
                "3. **Education**: If the job requires certain educational qualifications, check if the candidate's education matches these requirements.\n" +
                "\n" +
                "4. **Certifications**: Some jobs may require or prefer certain certifications. If the candidate has these, it should increase the matching score.\n" +
                "\n" +
                "5. **Keywords**: Some job descriptions may contain specific keywords or phrases that indicate important requirements or preferences. If these keywords also appear in the candidate's resume, it should increase the matching score.\n" +
                "\n" +
                "Remember to handle edge cases and exceptions. For example, a candidate may lack certain skills but have others that could potentially compensate. Or a candidate might have the required skills but lack work experience.";


        messageList.add(new Message("system",instructions));
        messageList.add(new Message("user","The job Is : "+job+ " and the Resume is: "+resume));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        //creating the request to send
        String requestBody;

        ObjectMapper mapper = new ObjectMapper();
        requestBody = mapper.writeValueAsString(request);


        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);


        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity, String.class);

        //extreact the message from response
        String responseBody = responseEntity.getBody();

        JSONObject jsonObject = new JSONObject(responseBody);

        // Get the 'choices' JSONArray
        JSONArray choicesArray = jsonObject.getJSONArray("choices");

        // Get the first object of 'choices' array
        JSONObject firstChoice = choicesArray.getJSONObject(0);

        // Get the 'message' JSONObject
        JSONObject messageObject = firstChoice.getJSONObject("message");

        // Extract 'role' and 'content'
        String content = messageObject.getString("content");

        //save in DB with the candidate info
        //getById
        //setScore
        return content;

    }    @Override
    public String resume(String cv) throws JsonProcessingException, JSONException {
        List<Message> messageList = new ArrayList<Message>();

        ChatgptRequest request = new ChatgptRequest(model, messageList);

        String resumeInstructions = conversationService.systemInstruction("C:\\Users\\elyes\\enet'com\\PFE\\Itpeac\\iria\\ariarh-middleoffice\\src\\main\\java\\com\\itpeac\\ariarh\\middleoffice\\web\\rest\\docs\\resumeCV.txt");

        String instructions = "Given the candidate's resume in any language as input, generate a very brief summary that highlights the most important details in the same given language as the resume. The summary should include:\n" +
                "\n" +
                "1. **Objective**: A one-sentence summary of the candidate's career objective or goal.\n" +
                "\n" +
                "2. **Skills**: A list of the candidate's key skills that are relevant to the job market.\n" +
                "\n" +
                "3. **Work Experience**: A very brief summary of the candidate's work history, including the roles they held, the duration of each role, and one sentence of key accomplishments.\n" +
                "\n" +
                "4. **Education**: A very brief summary of the candidate's educational background, including the degrees they hold and the institutions they attended.\n" +
                "\n" +
                "5. **Certifications**: A list of any certifications the candidate holds that are relevant to their job market.\n" +
                "\n" +
                "6. **Awards and Achievements**: A brief mention of any notable awards or achievements the candidate has earned in their career.\n" +
                "\n" +
                "Remember to maintain the privacy and confidentiality of the candidate's information while generating the summary.";


        messageList.add(new Message("system",resumeInstructions));
        messageList.add(new Message("user","The candidate Resume is: "+cv));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        //creating the request to send
        String requestBody;

        ObjectMapper mapper = new ObjectMapper();
        requestBody = mapper.writeValueAsString(request);


        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);


        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity, String.class);

        //extreact the message from response
        String responseBody = responseEntity.getBody();

        JSONObject jsonObject = new JSONObject(responseBody);

        // Get the 'choices' JSONArray
        JSONArray choicesArray = jsonObject.getJSONArray("choices");

        // Get the first object of 'choices' array
        JSONObject firstChoice = choicesArray.getJSONObject(0);

        // Get the 'message' JSONObject
        JSONObject messageObject = firstChoice.getJSONObject("message");

        // Extract 'role' and 'content'
        String content = messageObject.getString("content");

        //add the message

        return content;

    }
}
