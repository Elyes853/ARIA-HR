package com.itpeac.ariarh.middleoffice.web.rest;

import com.itpeac.ariarh.middleoffice.domain.Profile;
import com.itpeac.ariarh.middleoffice.service.ProfileService;
import com.itpeac.ariarh.middleoffice.service.dto.*;
import com.itpeac.ariarh.middleoffice.web.rest.mapper.ProfileLazyMapper;
import com.itpeac.ariarh.middleoffice.web.rest.mapper.ProfileMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
@Api(value = "/profiles", description = "gestion des profiles")
public class ProfileController {
    private static Logger LOG = LoggerFactory.getLogger(ProfileController.class);

    @Inject
    private ProfileService profileService;
    @Autowired
    private ProfileMapper profileMapper;
    @Autowired
    private ProfileLazyMapper profileLazyMapper;

    @ApiOperation(value = "Retourner la list des profil en mode lazy", response = PageResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "KO !!!")

    })
    @PostMapping(value = "/profiles", headers = "X-action=search")
    public PageResponse<ProfileLazyDTO> getAllProfiles(@RequestBody PageRequestByCriteria<ProfileCriteria> pageBycriteria) {
        MDC.put("action", "GET_PROFILES");
        LOG.info("critere {}]", pageBycriteria);

        final Long countTotal = profileService.countAllProfiles(pageBycriteria.criteria);
        final Page<Profile> page = profileService.getAllProfiles(pageBycriteria.toPageable(), pageBycriteria.criteria);
        PageResponse<ProfileLazyDTO> response = new PageResponse<ProfileLazyDTO>(
                ((countTotal) / pageBycriteria.lazyLoadEvent.rows) + (countTotal % pageBycriteria.lazyLoadEvent.rows),
                countTotal, profileLazyMapper.fromListEntityToListDto(page.getContent()));
        return response;
    }


    @PostMapping(value = "/profiles", headers = "X-action=create")
    @ApiOperation(value = "Create profile")
    public ResponseEntity<ProfileDTO> createProfile(@Valid @RequestBody ProfileDTO profileDTO) throws URISyntaxException {
        MDC.put("action", "CREATE_PROFILE");
        LOG.info("profile {}", profileDTO);

        if (profileDTO.getId() != null) {
            return ResponseEntity.badRequest().header("X-validation-error", "error.idexists").build();
        } else if (profileService.isCodeMetierUnique(profileMapper.fromDTOtoEntity(profileDTO))) {
            return ResponseEntity.badRequest().header("X-validation-error", "error.codeMetierAlreadyUsed").build();

        } else {
            ProfileDTO newProfile = profileMapper.fromEntityToDto(profileService.createProfile(profileMapper.fromDTOtoEntity(profileDTO)));

            return ResponseEntity.created(new URI("/api/profiles/" + newProfile.getId())).body(newProfile);
        }
    }

    @PutMapping("/profiles")
    @ApiOperation(value = "Update Profile")
    public ResponseEntity<ProfileDTO> updateProfile(@Valid @RequestBody ProfileDTO profileDTO) {
        MDC.put("action", "UPDATE_PROFILE");
        LOG.info("profile {}", profileDTO);

        ProfileDTO existingProfile = profileMapper.fromEntityToDto(profileService.findOneById(profileDTO.getId()));
        if (existingProfile == null) {
            return ResponseEntity.notFound().header("X-validation-error", "error.profileDoesNotExists").build();
        }
        if (existingProfile != null && (!existingProfile.getCodeMetier().equals(profileDTO.getCodeMetier()))) {
            if (profileService.isCodeMetierUnique(profileMapper.fromDTOtoEntity(profileDTO))) {
                return ResponseEntity.badRequest().header("X-validation-error", "error.codeMetierAlreadyUsed").build();
            }
        }
        ProfileDTO updatedProfile = profileMapper.fromEntityToDto(profileService.updateProfile(profileMapper.fromDTOtoEntity(profileDTO)));
        return ResponseEntity.status(HttpStatus.OK).body(updatedProfile);
    }


    @GetMapping("/profiles/{id}")
    @ApiOperation(value = "Requêter un profile par un id unique")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long id) {
        MDC.put("action", "GET_PROFILE_BY_ID");
        LOG.info("idProfile {}", id);

        ProfileDTO res = profileMapper.fromEntityToDto(profileService.findOneById(id));
        return res != null ? ResponseEntity.ok(res) : ResponseEntity.notFound().build();
    }


}
