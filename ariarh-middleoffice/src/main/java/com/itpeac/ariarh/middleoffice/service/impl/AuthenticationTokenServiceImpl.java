package com.itpeac.ariarh.middleoffice.service.impl;

import com.itpeac.ariarh.middleoffice.common.UserDetailsWithId;
import com.itpeac.ariarh.middleoffice.domain.Permission;
import com.itpeac.ariarh.middleoffice.domain.Profile;
import com.itpeac.ariarh.middleoffice.domain.User;
import com.itpeac.ariarh.middleoffice.repository.jpa.UserRepository;
import com.itpeac.ariarh.middleoffice.service.AuthenticationTokenService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AuthenticationTokenServiceImpl implements AuthenticationTokenService {

    @Inject
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetailsWithId findUserByEmail(String email) {

        Optional<User> res = Optional.of(userRepository
                .findOneByEmail(email))
                .filter(Optional::isPresent)
                .map(Optional::get);
        User user = res.isPresent() ? res.get() : null;
        if (user != null) {
            return new UserDetailsWithId(email, email, user.getActivated(), true, true, user.getNonLocked(),
                    getGrantedAuthorities(getPrivileges(user.getProfile())), user.getId(), user.getNom(), user.getPrenom(), user.getProfile().getId());
        }
        return null;
    }

    private List<String> getPrivileges(Profile profile) {
        return profile != null ? profile.getPermissions().stream()
                .map(Permission::getRole).collect(Collectors.toList())
                : Collections.emptyList();
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }


}
