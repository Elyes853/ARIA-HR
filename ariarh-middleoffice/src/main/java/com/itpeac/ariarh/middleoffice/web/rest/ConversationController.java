package com.itpeac.ariarh.middleoffice.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.itpeac.ariarh.middleoffice.domain.*;
import com.itpeac.ariarh.middleoffice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/conversation")
@CrossOrigin(origins = "http://localhost:4200")
public class ConversationController {
    @Autowired
    ScoreAndResumeService scoreAndResumeService;
    @Autowired
    ConversationService conversationService;
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    @Autowired
    ConversationJwtTokenService conversationJwtTokenService;


    @Value("${chatgpt.model}")
    private String model;

    @Value("${chatgpt.api.url}")
    private String apiUrl;

    @Value("${chatgpt.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Autowired
    public ConversationController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }




    @PostMapping()

        public ResponseEntity<?> createConversation(@RequestParam String token){
            try{


                //check the token Must uncomment
            /*    if (conversationJwtTokenService.isJwtValid(token) == false){
                    //redirection to unauthorized
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vous n'êtes pas autorisés");
                }*/

                //token decoding and getting the offer Id from it
                Map<String, Long> decodedToken = conversationJwtTokenService.decodeToken(token);
                Long offerId = decodedToken.get("offerId");
                Long candidateId = decodedToken.get("candidateId");




                Candidate candidate = userService.getCandidateById(candidateId);
                candidate.setIsOpen(true);

                Conversation newConversation = new Conversation();
                newConversation.setCandidate(candidate);

                conversationService.saveConversation(newConversation);

                //refresh
/*
                conversationService.refresh(newConversation);
*/

                //instruction Message
                String instructionText = conversationService.systemVariables(offerId, candidate.getName());
                Message instructionMessage = new Message("system", instructionText);
                instructionMessage.setConversation_id(newConversation);
                messageService.saveMessage(instructionMessage);





                //simulation du message Bonjour
                Message bonjourMessage = new Message();

                bonjourMessage.setContent("Bonjour");
                bonjourMessage.setRole("user");
                bonjourMessage.setConversation_id(newConversation);
                bonjourMessage.setSendingTime(LocalDateTime.now());
                messageService.saveMessage(bonjourMessage);

                conversationService.refresh(newConversation);


                //begin chatgpt response
                List messageList = newConversation.getMessagesList();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(apiKey);
                //creating the request to send
                ChatgptRequest request = new ChatgptRequest(model, messageList);
                String requestBody;
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());

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
                String role = messageObject.getString("role");
                //save message in DB
                Message responseMessage = new Message();
                responseMessage.setContent(content);
                responseMessage.setRole(role);
                responseMessage.setConversation_id(newConversation);
                responseMessage.setSendingTime(LocalDateTime.now());
                Message savedResponseMessage = messageService.saveMessage(responseMessage);
                conversationService.refresh(newConversation);


                return ResponseEntity.ok(newConversation);
            } catch (Exception e){
                e.getMessage();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }

   /* @GetMapping("/{id}")
    public ResponseEntity<?> getConversation(@PathVariable Long id) {
        try{
            // Fetch the conversation from the database
            Conversation conversation = conversationService.findById(id);

            // The messagesList should already be fetched due to FetchType.EAGER
            List<Message> messages = conversation.getMessagesList();

            // Do something with the messages if needed...

            // Return the conversation wrapped in a ResponseEntity
            return ResponseEntity.ok(conversation);
        } catch (Exception e){
            e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }*/


    @GetMapping("/{candidateId}")
    public ResponseEntity<?> getConversation(@PathVariable Long candidateId) {
        try{
            // Fetch the conversation from the database
            Candidate candidate = userService.getCandidateById(candidateId);

            // The messagesList should already be fetched due to FetchType.EAGER
            Conversation conversation = candidate.getConversationList().get(0);

            List<Message> messages = conversation.getMessagesList();


            // Do something with the messages if needed...

            // Return the conversation wrapped in a ResponseEntity
            return ResponseEntity.ok(messages);
        } catch (Exception e){
            e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }




    @PostMapping("/message")
    public ResponseEntity<?> sendMessage(@RequestBody Message message) {
        try {

/*          Conversation conversation = conversationService.findById(id);
            messageService.saveMessage(message);
            conversationService.refresh(conversation);content = "hi\n"... View
            */


            message.setSendingTime(LocalDateTime.now());
            messageService.saveMessage(message);
            messageService.refresh(message);
            Conversation conversation = message.getConversation_id();




            //begin chatgpt response
            List messageList = conversation.getMessagesList();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);
            //creating the request to send
            ChatgptRequest request = new ChatgptRequest(model, messageList);
            String requestBody;
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

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
            String role = messageObject.getString("role");
            //save message in DB
            Message responseMessage = new Message();
            responseMessage.setContent(content);
            responseMessage.setRole(role);
            responseMessage.setConversation_id(conversation);
            responseMessage.setSendingTime(LocalDateTime.now());
            Message savedResponseMessage = messageService.saveMessage(responseMessage);

            return ResponseEntity.ok(savedResponseMessage);
/*
            return ResponseEntity.status(HttpStatus.OK).body(responseEntity.getBody());
*/
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred: " + e.getMessage());
            }

    }
}





