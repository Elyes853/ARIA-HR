package com.itpeac.ariarh.middleoffice.service;

import javax.validation.Valid;

import com.itpeac.ariarh.middleoffice.domain.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itpeac.ariarh.middleoffice.service.dto.ProfileCriteria;

public interface ProfileService {
    Long countAllProfiles(ProfileCriteria criteria);

    Profile findOneById(Long id);

    Profile findOneByCodeMetier(String codeMetier);

    Profile createProfile(Profile profile);

    Profile updateProfile(Profile profile);

    boolean isCodeMetierUnique(@Valid Profile profile);

    Page<Profile> getAllProfiles(Pageable pageable, ProfileCriteria profileCriteria);
}
