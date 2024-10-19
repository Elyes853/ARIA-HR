package com.itpeac.ariarh.middleoffice.web.rest;

import com.itpeac.ariarh.middleoffice.domain.*;
import com.itpeac.ariarh.middleoffice.repository.jpa.CVRepository;
import com.itpeac.ariarh.middleoffice.repository.jpa.CandidateRepository;
import com.itpeac.ariarh.middleoffice.service.*;
import com.itpeac.ariarh.middleoffice.service.dto.CandidateDTO;
import com.itpeac.ariarh.middleoffice.service.dto.PageRequestByCriteria;
import com.itpeac.ariarh.middleoffice.service.dto.PageResponse;
import com.itpeac.ariarh.middleoffice.service.dto.UserDTO;
import com.itpeac.ariarh.middleoffice.web.rest.mapper.CandidateMapper;
import com.itpeac.ariarh.middleoffice.web.rest.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value = "/users", description = "gestion d'utilisateurs")
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Inject
    private UserService userService;

    @Inject
    private MailService mailService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CVRepository cvRepository;

    @Autowired
    private OfferService offerService;

    @Autowired
    private ConversationJwtTokenService conversationJwtTokenService;
    @Autowired
    private CandidateMapper candidateMapper;



    @Autowired
    private ScoreAndResumeService scoreAndResumeService;

    @Autowired
    private S3Services s3Service;

    @ApiOperation(value = "Requêter la liste d'utilisateur en mode lazy", response = PageResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Something bad happened !!!")

    })

    @PostMapping(value = "/users", headers = "X-action=search")
    public ResponseEntity<PageResponse<UserDTO>> getAllUsers(@RequestBody PageRequestByCriteria<UserDTO> pageBycriteria) {
        MDC.put("action", "GET_USERS");
        LOG.info("GET ALL USER BY CRITERIA {}", pageBycriteria);

        try {
            final Long countTotal = userService.countAllUsers(userMapper.fromDTOtoEntity(pageBycriteria.criteria));
            final Page<User> page = userService.getAllUsers(pageBycriteria.toPageable(), userMapper.fromDTOtoEntity(pageBycriteria.criteria));
            PageResponse<UserDTO> response = new PageResponse<>(
                    ((countTotal) / pageBycriteria.lazyLoadEvent.rows) + (countTotal % pageBycriteria.lazyLoadEvent.rows),
                    countTotal, userMapper.fromListEntityToListDto(page.getContent()));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/public/createusers", headers = "X-action=create")
    @ApiOperation(value = "Create user")
    public ResponseEntity<UserDTO> createUser( @RequestBody UserDTO userDTO) {
        MDC.put("action", "CREATE_USER");
        LOG.info("CREATE USER {}", userDTO);

        try {
            if (userDTO.getId() != null) {
                return ResponseEntity.badRequest().header("X-validation-error", "error.idexists").build();
            } else if (userService.isLoginUnique(userMapper.fromDTOtoEntity(userDTO))) {
                return ResponseEntity.badRequest().header("X-validation-error", "error.emailAlreadyUsed").build();
            } else {
                UserDTO newUser = userMapper.fromEntityToDto(userService.createUser(userService.buildUser(userDTO)));
                // [TODO] send subscription Email here
                mailService.sendCreationEmail(newUser);
                return ResponseEntity.ok().body(newUser);
            }
        } catch (Exception e) {
            LOG.error("ERROR CREATE USER", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/addCandidat", headers = "X-action=create")
    public ResponseEntity<Candidate> addCandidate(@RequestBody CandidateRequest candidateRequest) {
        try {

            CandidateDTO candidateDTO = candidateRequest.getUser();
            Long offerId = candidateRequest.getOfferId();
            System.out.println(candidateDTO.getName());


            Offer offer = offerService.getOfferById(offerId);
            System.out.println(offer.getTitle());

            Candidate candidateEntity = candidateMapper.fromDTOtoEntity(candidateDTO);


            //generate resume and matching score
            String offerDetails = offer.toString();

            Long CVId = candidateEntity.getCv().getId();
            CV cv = cvRepository.getById(CVId);
            String textCV = cv.getBody();



            String candidateScore = scoreAndResumeService.score(offerDetails,textCV);
            String candidateResume = scoreAndResumeService.resume(textCV);
            System.out.print("\n\n\n\n\n\n\n\n\n\n");
            System.out.print(candidateScore);
            System.out.print("\n\n\n\n\n\n\n\n\n\n");
            System.out.print(candidateResume);


            candidateEntity.setScore(candidateScore);
            candidateEntity.setResume(candidateResume);
            //end




            Candidate savedCandidate = userService.addCandidate(candidateEntity);

            String token = conversationJwtTokenService.generateJwt(savedCandidate.getId(),offerId);
            savedCandidate.setToken(token);
            userService.addCandidate(savedCandidate);

            String lien = "http://localhost:4200/conversation?token="+token;

            mailService.sendEmail(savedCandidate, lien);

            offer.getCandidatesList().add(savedCandidate);
            offerService.saveOffer(offer);


            return new ResponseEntity<>(savedCandidate, HttpStatus.CREATED);
        } catch (Exception e) {
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users")
    //@PreAuthorize
    @ApiOperation(value = "Requêter un utilisateur par u nid unique")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        MDC.put("action", "UPDATE_USER");
        LOG.info("UPDATE USER {}", userDTO);

        try {
            UserDTO existingUser = userMapper.fromEntityToDto(userService.findOneById(userDTO.getId()));
            if (existingUser == null) {
                return ResponseEntity.notFound().header("X-validation-error", "error.userDoesNotExists").build();
            }
            if (!existingUser.getId().equals(userDTO.getId())) {
                return ResponseEntity.badRequest().header("X-validation-error", "error.emailAlreadyUsed").build();
            }
            UserDTO updatedUser = userMapper.fromEntityToDto(userService.updateUser(userMapper.fromDTOtoEntity(userDTO)));
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        } catch (Exception e) {
            LOG.error("ERROR UPDATE USER", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PutMapping("/users/{id}/pwd")
    public ResponseEntity<UserDTO> resetUserPassword(@PathVariable Long id) {
        MDC.put("action", "RESET_PWD_USER");
        LOG.info("idUser {}", id);
        UserDTO user = userMapper.fromEntityToDto(userService.findOneById(id));
        UserDTO userPwdUpdated = userMapper.fromEntityToDto(userService.resetPassword(userMapper.fromDTOtoEntity(user)));
        if (userPwdUpdated == null) {
            return ResponseEntity.badRequest().header("X-password-error", "error.passwordNotChanged").build();
        }
        mailService.sendPasswordResetMail(userPwdUpdated);

        return ResponseEntity.ok().body(userPwdUpdated);
    }

    /**
     * Gets a list of all roles.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/users/authorities")
    @ApiOperation(value = "Requêter un utilisateur par un id unique")
    public List<String> getAuthorities() {
        return null;
    }

    @GetMapping("/users/{id}")
    @ApiOperation(value = "Requêter un utilisateur par u nid unique")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        MDC.put("action", "GET_USER_BY_ID");
        LOG.info("GET USER BY ID {}", id);
        UserDTO res = userMapper.fromEntityToDto(userService.findOneById(id));
        return res != null ? ResponseEntity.ok(res) : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "upload user picture")
    @PostMapping("/users/{id}/picture")
    public ResponseEntity<String> uploadMultipleFiles(@RequestParam("picture") MultipartFile file, @PathVariable Long id) {
        MDC.put("action", "UPDATE_USER_PICTURE");
        LOG.info("idUser {}", id);
        try {
            userService.storeUserPicture(file.getInputStream(), id);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(String.format("/users/%s/picture", id));
    }

    @ApiOperation(value = "download user picture")
    @GetMapping("/users/{id}/picture")
    public ResponseEntity<Resource> loadFileAsResource(@PathVariable Long id) {
        MDC.put("action", "GET_USER_PICTURE");
        LOG.info("idUser {}", id);
        Resource r = userService.loadFileAsResource(id);
        return r != null ? ResponseEntity.ok(r) : ResponseEntity.notFound().build();
    }

    @GetMapping("/candidates/{id}")
    public ResponseEntity<?> getCandidate(@PathVariable Long id){
        try {
            Candidate candidate = userService.getCandidateById(id);

            String cvPath = candidate.getCv().getPath();
            Resource cvImage = s3Service.getObject(cvPath);

            return ResponseEntity.ok(candidate);
        }catch(Error e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/candidates/{id}/cv")
    public ResponseEntity<?> getCandidateCV(@PathVariable Long id){
        try {
            Candidate candidate = userService.getCandidateById(id);
            String cvPath = candidate.getCv().getPath();
            Resource originalCV = s3Service.getObject(cvPath);
            if (originalCV == null) {
                throw new RuntimeException("Failed to download file");
            }


            String contentType = s3Service.determineContentType(cvPath);


            String filename = cvPath.substring(cvPath.lastIndexOf("/") + 1);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(originalCV);

   /*         return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(originalCV);*/
        }catch(Error e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PostMapping("/send-email")
    public ResponseEntity<Void> sendEmail(@RequestBody Candidate candidate,String lien) {
        String templateName = "emailTemplate";
        String titleKey = "email.title";
try {

    Candidate foundCandidate =  userService.getCandidateById(candidate.getId());
        String content = mailService.getContentFromTemplateAndReplaceVariables(foundCandidate, lien);
    mailService.sendEmail("elyes.hmouda@gmail.com", "Invitation Entretien", content, false, true);
    return ResponseEntity.ok().build();
}catch (Exception e){

    LOG.error(e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


}
    }


    /*    @PostMapping("/send-email")
    public ResponseEntity<Void> sendEmail(@RequestBody Candidate candidate) {
        String templateName = "emailTemplate";
        String titleKey = "email.title";
try {
    //method that generates TOken
    String token = "";

    String lien = "https://matias.me/nsfw/";

    //delete the following
    Candidate tempCandidate = new Candidate();
    candidate.setId(3L);
    //delete above

    Candidate foundCandidate =  userService.getCandidateById(candidate.getId());
        String content = mailService.getContentFromTemplateAndReplaceVariables(foundCandidate, lien);
    mailService.sendEmail("elyes.hmouda@gmail.com", "Invitation Entretien", content, false, true);
    return ResponseEntity.ok().build();
}catch (Exception e){

    LOG.error(e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


}
    }*/




/*    @GetMapping("/candidates/{id}/cv")
    public ResponseEntity<?> getCandidateCV(@PathVariable Long id){
        try {
            Candidate candidate = userService.getCandidateById(id);
            String cvPath = candidate.getCv().getPath();
            Resource cvImage = s3Service.getObject(cvPath);

            return ResponseEntity.ok(cvImage);
        }catch(Error e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }*/









}
		