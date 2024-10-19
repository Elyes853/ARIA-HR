package com.itpeac.ariarh.middleoffice.service;

import com.itpeac.ariarh.middleoffice.domain.Candidate;
import com.itpeac.ariarh.middleoffice.domain.User;
import com.itpeac.ariarh.middleoffice.service.dto.UserDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.InputStream;
import java.util.List;

public interface UserService {

    Page<User> getAllUsers(Pageable pageable, User user);

    Long countAllUsers(User user);

    boolean isLoginUnique(User user);

    boolean isEmailUnique(User user);

    User findOneByEmail(String email);

    User findOneById(Long id);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(String login);

    void storeUserPicture(InputStream picture, Long id);

    Resource loadFileAsResource(Long id);

    void changePassword(String currentClearTextPassword, String newPassword);

    User resetPassword(User user);

    List<User> getAllUser();

    User buildUser(UserDTO userDTO);

    Candidate addCandidate(Candidate candidate);

    Candidate getCandidateById(Long id);

    void refresh(Candidate candidate);



}
