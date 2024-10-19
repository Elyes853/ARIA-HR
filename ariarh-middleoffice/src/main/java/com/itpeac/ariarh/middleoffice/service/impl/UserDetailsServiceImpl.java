package com.itpeac.ariarh.middleoffice.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.itpeac.ariarh.middleoffice.common.UserDetailsWithId;
import com.itpeac.ariarh.middleoffice.domain.Permission;
import com.itpeac.ariarh.middleoffice.domain.Profile;
import com.itpeac.ariarh.middleoffice.domain.User;
import com.itpeac.ariarh.middleoffice.repository.jpa.ProfileRepository;
import com.itpeac.ariarh.middleoffice.repository.jpa.UserLoginRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserLoginRepository userLoginRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userLoginRepository.findOneByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        String password = user.getPassword();
        boolean enabled = user.getActivated();
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = user.getNonLocked();
        Profile profile = profileRepository.findOneById(user.getProfile().getId()).get();
        List<GrantedAuthority> authorities = getGrantedAuthorities(getPrivileges(profile));

        return new UserDetailsWithId(username, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities, user.getId(), user.getNom(), user.getPrenom(),
                 user.getProfile().getId());

    }

    private List<String> getPrivileges(Profile profile) {
        List<Permission> permissions = profile.getPermissions();

        return profile != null
                ? permissions.stream().map(permission -> permission.getRole()).collect(Collectors.toList())
                : Collections.emptyList();
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    public static String getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

}
