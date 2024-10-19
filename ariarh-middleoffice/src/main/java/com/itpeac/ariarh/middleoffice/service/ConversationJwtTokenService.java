package com.itpeac.ariarh.middleoffice.service;

import java.util.Map;

public interface ConversationJwtTokenService {
    String generateJwt(Long userId, Long offerId/*, Long testId*/);

    boolean isJwtValid(String jwt) ;

    Map<String, Long> decodeToken(String jwt);

}
