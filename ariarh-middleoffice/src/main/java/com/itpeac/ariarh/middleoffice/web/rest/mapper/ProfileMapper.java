package com.itpeac.ariarh.middleoffice.web.rest.mapper;

import com.itpeac.ariarh.middleoffice.domain.Profile;
import com.itpeac.ariarh.middleoffice.service.dto.ProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileMapper implements Mapper<Profile, ProfileDTO> {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public ProfileDTO fromEntityToDto(Profile profile) {
        if (profile != null) {
            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setId(profile.getId());
            profileDTO.setCodeMetier(profile.getCodeMetier());
            profileDTO.setDescription(profile.getDescription());
            profileDTO.setCreatedBy(profile.getCreatedBy());
            profileDTO.setLastModifiedBy(profile.getLastModifiedBy());
            profileDTO.setCreatedDate(profile.getCreatedDate());
            profileDTO.setLastModifiedDate(profile.getLastModifiedDate());

             /*if (profile.getPermissions() != null) {
                profileDTO.setPermissions(permissionMapper.fromListEntityToListDto(profile.getPermissions()));
             }*/
            return profileDTO;
        }
        return null;
    }

    @Override
    public Profile fromDTOtoEntity(ProfileDTO profileDTO) {
        if (profileDTO != null) {
            Profile profile = new Profile();
            profile.setId(profileDTO.getId());
            profile.setCodeMetier(profileDTO.getCodeMetier());
            profile.setDescription(profileDTO.getDescription());
            profile.setCreatedBy(profileDTO.getCreatedBy());
            profile.setLastModifiedBy(profileDTO.getLastModifiedBy());
            profile.setCreatedDate(profileDTO.getCreatedDate());
            profile.setLastModifiedDate(profileDTO.getLastModifiedDate());

            /*if (profile.getPermissions() != null) {
                profile.setPermissions(permissionMapper.fromListDtoToEntity(profileDTO.getPermissions()));
            }*/
            return profile;
        }
        return null;
    }

    public List<ProfileDTO> fromListDtoToListEntity(List<Profile> profiles) {
        return profiles.stream().map(data -> fromEntityToDto(data)).collect(Collectors.toList());
    }

    public List<Profile> fromListEntityToListDto(List<ProfileDTO> profileDTOS) {
        return profileDTOS.stream().map(data -> fromDTOtoEntity(data)).collect(Collectors.toList());
    }
}
