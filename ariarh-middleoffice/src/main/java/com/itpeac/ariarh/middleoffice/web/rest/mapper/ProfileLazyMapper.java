package com.itpeac.ariarh.middleoffice.web.rest.mapper;

import com.itpeac.ariarh.middleoffice.domain.Profile;
import com.itpeac.ariarh.middleoffice.service.dto.ProfileLazyDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileLazyMapper implements Mapper<Profile, ProfileLazyDTO> {

    @Override
    public ProfileLazyDTO fromEntityToDto(Profile profile) {
        if(profile != null) {
            ProfileLazyDTO profileLazyDTO = new ProfileLazyDTO();
            profileLazyDTO.setId(profile.getId());
            profileLazyDTO.setCodeMetier(profile.getCodeMetier());
            profileLazyDTO.setDescription(profile.getDescription());
            profileLazyDTO.setCreatedBy(profile.getCreatedBy());
            profileLazyDTO.setLastModifiedBy(profile.getLastModifiedBy());
            profileLazyDTO.setCreatedDate(profile.getCreatedDate());
            profileLazyDTO.setLastModifiedDate(profile.getLastModifiedDate());

            return profileLazyDTO;
        }
        return null;
    }

    @Override
    public Profile fromDTOtoEntity(ProfileLazyDTO profileLazyDTO) {
        if(profileLazyDTO != null) {
            Profile profile = new Profile();
            profile.setId(profileLazyDTO.getId());
            profile.setCodeMetier(profileLazyDTO.getCodeMetier());
            profile.setDescription(profileLazyDTO.getDescription());
            profile.setCreatedBy(profileLazyDTO.getCreatedBy());
            profile.setLastModifiedBy(profileLazyDTO.getLastModifiedBy());
            profile.setCreatedDate(profileLazyDTO.getCreatedDate());
            profile.setLastModifiedDate(profileLazyDTO.getLastModifiedDate());

            return profile;
        }
        return null;
    }

    public List<ProfileLazyDTO> fromListEntityToListDto(List<Profile> profiles) {
        return profiles.stream().map(data -> fromEntityToDto(data)).collect(Collectors.toList());
    }

    public List<Profile> fromListDtoToListEntity(List<ProfileLazyDTO> profileLazyDTOS) {
        return profileLazyDTOS.stream().map(data -> fromDTOtoEntity(data)).collect(Collectors.toList());
    }
}
