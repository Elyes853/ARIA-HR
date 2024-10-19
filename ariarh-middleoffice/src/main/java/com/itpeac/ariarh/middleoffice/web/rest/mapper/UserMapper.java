package com.itpeac.ariarh.middleoffice.web.rest.mapper;

import com.itpeac.ariarh.middleoffice.domain.User;
import com.itpeac.ariarh.middleoffice.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper implements Mapper<User, UserDTO>{

    @Autowired
    private ProfileMapper profileMapper;

    @Override
    public UserDTO fromEntityToDto(User user) {
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setNom(user.getNom());
            userDTO.setPrenom(user.getPrenom());
            //userDTO.setPassword(user.getPassword());
            userDTO.setDateNaissance(user.getDate());
            userDTO.setActivated(user.getActivated());
            userDTO.setNonLocked(user.getNonLocked());
            userDTO.setCreatedBy(user.getCreatedBy());
            userDTO.setLastModifiedBy(user.getLastModifiedBy());
            userDTO.setCreatedDate(user.getCreatedDate());
            userDTO.setLastModifiedDate(user.getLastModifiedDate());

            if(user.getProfile() != null) {
                userDTO.setProfile(profileMapper.fromEntityToDto(user.getProfile()));
            }


            return userDTO;
        }
        return null;
    }

    @Override
    public User fromDTOtoEntity(UserDTO userDTO) {
        if (userDTO != null) {
            User user = new User();
            user.setId(userDTO.getId());
            user.setEmail(userDTO.getEmail());
            user.setNom(userDTO.getNom());
            user.setPrenom(userDTO.getPrenom());
            user.setPassword(userDTO.getPassword());
            user.setTelephone(userDTO.getTelephone());
            user.setDate(userDTO.getDateNaissance());
            user.setActivated(userDTO.getActivated());
            user.setNonLocked(userDTO.getNonLocked());
            user.setCreatedBy(userDTO.getCreatedBy());
            user.setLastModifiedBy(userDTO.getLastModifiedBy());
            user.setCreatedDate(userDTO.getCreatedDate());
            user.setLastModifiedDate(userDTO.getLastModifiedDate());

            if(userDTO.getProfile() != null) {
                user.setProfile(profileMapper.fromDTOtoEntity(userDTO.getProfile()));
            }


            return user;
        }
        return null;
    }

    public List<UserDTO> fromListEntityToListDto(List<User> users) {
        return users.stream().map(data -> fromEntityToDto(data)).collect(Collectors.toList());
    }

    public List<User> fromListDtoToListEntity(List<UserDTO> userDTOS) {
        return userDTOS.stream().map(data -> fromDTOtoEntity(data)).collect(Collectors.toList());
    }
}
