package com.itpeac.ariarh.middleoffice.service.impl;

import com.itpeac.ariarh.middleoffice.domain.Candidate;
import com.itpeac.ariarh.middleoffice.domain.User;
import com.itpeac.ariarh.middleoffice.repository.jpa.CandidateRepository;
import com.itpeac.ariarh.middleoffice.repository.jpa.UserRepository;
import com.itpeac.ariarh.middleoffice.repository.jpa.UserSpecifications;
import com.itpeac.ariarh.middleoffice.service.UserService;
import com.itpeac.ariarh.middleoffice.service.dto.UserDTO;
import com.itpeac.ariarh.middleoffice.web.rest.errors.InvalidPasswordException;
import com.itpeac.ariarh.middleoffice.web.rest.mapper.UserMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    static private final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private UserMapper userMapper;


    @Override
    public Page<User> getAllUsers(Pageable pageable, User user) {
        return userRepository.findAll(UserSpecifications.byCriteriaSpec(user), pageable);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Long countAllUsers(User user) {
        return userRepository.count(UserSpecifications.byCriteriaSpec(user));
    }

    public boolean isEmailUnique(User user) {
        return false;
    }

    public boolean isLoginUnique(User user) {
        return userRepository.findOneByEmail(user.getEmail().toLowerCase()).isPresent();
    }

    public User updateUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User createUser(User user) {
        // init data
        String p = user.getPassword();
        user.setPassword(passwordEncoder.encode(p));
        user.setNbAttempts(0);

        User u = userRepository.saveAndFlush(user);
        LOG.debug("Created Information for User: {}", user);
        u.setPassword(p);
        return u;
    }

    public void deleteUser(String login) {
        userRepository.findOneByEmail(login).ifPresent(user -> {
            userRepository.delete(user);
            LOG.debug("Deleted User: {}", user);
        });
    }

    @Override
    public User findOneByEmail(String email) {
        Optional<User> res = userRepository.findOneByEmail(email);
        return res.isPresent() ? res.get() : null;
    }

    @Override
    public User findOneById(Long id) {
        Optional<User> res = userRepository.findOneById(id);
        return res.isPresent() ? res.get() : null;
    }

    @Override
    public void storeUserPicture(InputStream picture, Long id) {
        try {
            userRepository.updateUserPicture(IOUtils.toByteArray(picture), id);
        } catch (IOException e) {

        }
    }

    public Resource loadFileAsResource(Long id) {
        byte[] pic = userRepository.getUserPicture(id);
        return pic != null ? new ByteArrayResource(pic) : null;
    }

    public void changePassword(String currentClearTextPassword, String newPassword) {
        Optional.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).map(String.class::cast)
                .flatMap(userRepository::findOneByEmail).ifPresent(user -> {
                    String currentEncryptedPassword = user.getPassword();
                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                        throw new InvalidPasswordException();
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedPassword);
                    userRepository.saveAndFlush(user);
                });
    }

    @Override
    public User resetPassword(User user) {
        //init data
        String p = RandomStringUtils.randomAlphanumeric(10);
        user.setPassword(passwordEncoder.encode(p));
        User u = userRepository.saveAndFlush(user);

        u.setPassword(p);
        return u;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User buildUser(UserDTO userDTO) {
        User user = userMapper.fromDTOtoEntity(userDTO);
        return user;
    }

    @Override
    public Candidate addCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void refresh(Candidate candidate) {
        entityManager.refresh(candidate);

    }



}
