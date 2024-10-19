package com.itpeac.ariarh.middleoffice.common;

import com.itpeac.ariarh.middleoffice.configuration.security.AuthoritiesConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Get Spring security context to access user data security infos
 */

public final class UserContext {


    private UserContext() {
    }

    /**
     * Get the current username. Note that it may not correspond to a username that
     * currently exists in your account repository; it could be a spring security
     * 'anonymous user'.
     *
     * @return the current user's username, or 'anonymousUser'.
     * @see org.springframework.security.web.authentication.AnonymousAuthenticationFilter
     */
    public static String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            if(auth.getPrincipal()!=null){
                Object principal = auth.getPrincipal();

                if (principal instanceof UserDetails) {
                    return ((UserDetails) principal).getUsername();
                }

                return principal.toString();
            }
        }

        return AuthoritiesConstants.ANONYMOUS_USER;
    }

    public static Long getId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            UserDetailsWithId principal =(UserDetailsWithId) auth.getPrincipal();

            if (principal instanceof UserDetailsWithId) {
                return principal.getId();
            }
        }
        return null;

    }

    /**
     * Retrieve the current UserDetails bound to the current thread by Spring Security, if any.
     */
    public static UserDetails getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) auth.getPrincipal());
        }

        return null;
    }

    public static UserDetailsWithId getPrincipal() {
        Object ud = getUserDetails();
        if (ud != null && UserDetailsWithId.class.isAssignableFrom(ud.getClass())) {
            return ((UserDetailsWithId) ud);
        }

        return null;
    }

    public static boolean hasPermission(String permission) {
        return SecurityContextHolder.getContext().getAuthentication().
                getAuthorities().stream().filter(auth -> auth.getAuthority().equals(permission)).findAny().isPresent();
    }

}
