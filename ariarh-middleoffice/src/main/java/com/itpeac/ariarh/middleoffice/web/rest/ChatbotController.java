package com.itpeac.ariarh.middleoffice.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itpeac.ariarh.middleoffice.domain.ChatgptRequest;
import com.itpeac.ariarh.middleoffice.domain.Conversation;
import com.itpeac.ariarh.middleoffice.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ChatbotController {

    @Value("${chatgpt.model}")
    private String model;

    @Value("${chatgpt.api.url}")
    private String apiUrl;

    @Value("${chatgpt.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Autowired
    public ChatbotController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "/ask", method = RequestMethod.GET)
    public String ask(@RequestParam String query) {
        //add the query to messagesList
        Message sentMessage = new Message("user", query);

        Conversation conversation = new Conversation();
        List<Message> messageList = new ArrayList<>();
        conversation.setMessagesList(messageList);

        //add the new message to messageList
        messageList.add(sentMessage);



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        ChatgptRequest request = new ChatgptRequest(model, messageList);

        String requestBody;
        try {
            ObjectMapper mapper = new ObjectMapper();
            requestBody = mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Error processing request: " + e.getMessage();
        }

        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity, String.class);

            System.out.println(responseEntity.getBody());

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            } else {
                return "Unexpected response from the chatbot API";
            }
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return "Error communicating with the chatbot API: " + e.getMessage();
        }
    }



}
