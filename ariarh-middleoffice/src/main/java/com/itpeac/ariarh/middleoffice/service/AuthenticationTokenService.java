package com.itpeac.ariarh.middleoffice.service;

import com.itpeac.ariarh.middleoffice.common.UserDetailsWithId;

public interface AuthenticationTokenService {
    UserDetailsWithId findUserByEmail(String email);


}
