package com.itpeac.ariarh.middleoffice.service.impl;

import java.util.Optional;

import javax.validation.Valid;

import com.itpeac.ariarh.middleoffice.repository.jpa.ProfileSpecification;
import com.itpeac.ariarh.middleoffice.web.rest.mapper.ProfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.itpeac.ariarh.middleoffice.domain.Profile;
import com.itpeac.ariarh.middleoffice.repository.jpa.ProfileRepository;
import com.itpeac.ariarh.middleoffice.service.ProfileService;
import com.itpeac.ariarh.middleoffice.service.dto.ProfileCriteria;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileMapper profileMapper;

    static private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Page<Profile> getAllProfiles(Pageable pageable, ProfileCriteria profileCriteria) {

        if (profileCriteria != null) {
            return profileRepository.findAll(ProfileSpecification.byCriteriaSpec(profileCriteria), pageable);
        }
        return profileRepository.findAll(pageable);
    }

    @Override
    public Long countAllProfiles(ProfileCriteria profileCriteria) {
        if (profileCriteria != null) {
            return profileRepository.count(ProfileSpecification.byCriteriaSpec(profileCriteria));
        }
        return profileRepository.count();
    }

    @Override
    public Profile findOneById(Long id) {
        Optional<Profile> profile = profileRepository.findOneById(id);

        return profile.isPresent() ? profile.get() : null;

    }

    @Override
    public Profile findOneByCodeMetier(String codeMetier) {
        Optional<Profile> res = profileRepository.findOneByCodeMetier(codeMetier);
        return res.isPresent() ? res.get() : null;
    }

    @Override
    public Profile createProfile(Profile profile) {

        Profile p = profileRepository.saveAndFlush(profile);
        return p;

    }

    @Override
    public Profile updateProfile(Profile profile) {
        Optional<Profile> res = Optional.of(profileRepository.findOneById(profile.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(data -> {
                    log.debug("Changed Information for Profile: {}", data);
                    data.setPermissions(profile.getPermissions());
                    return profileRepository.saveAndFlush(data);
                });
        return res.isPresent() ? res.get() : null;
    }

    @Override
    public boolean isCodeMetierUnique(@Valid Profile profile) {
        return profileRepository.findOneByCodeMetier(profile.getCodeMetier().toLowerCase()).isPresent();

    }


}
