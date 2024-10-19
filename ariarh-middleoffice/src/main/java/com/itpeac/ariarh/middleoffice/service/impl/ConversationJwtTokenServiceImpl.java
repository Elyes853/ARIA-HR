package com.itpeac.ariarh.middleoffice.service.impl;

import com.itpeac.ariarh.middleoffice.domain.Candidate;
import com.itpeac.ariarh.middleoffice.repository.jpa.CandidateRepository;
import com.itpeac.ariarh.middleoffice.service.ConversationJwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConversationJwtTokenServiceImpl implements ConversationJwtTokenService {


    @Value("${secretkey}")
    private String secretKey;


    @Autowired
    CandidateRepository candidateRepository;

    @Override
    public String generateJwt(Long candidateId, Long offerId/*, Long testId*/) {
        String jwt = Jwts.builder()
                .setSubject(candidateId.toString())
                .claim("offerId", offerId)
                /*.claim("testId", testId)*/
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
        return jwt;
    }
    @Override
    public boolean isJwtValid(String jwt) {
        Candidate candidate = candidateRepository.findByToken(jwt);
        if (candidate == null || candidate.getIsOpen()) {
            return false;
        }
        candidate.setIsOpen(true);
        candidate.setToken(renewJwtExpiration(jwt));
        candidateRepository.save(candidate);
        return true;
    }



    private String renewJwtExpiration(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())).parseClaimsJws(jwt).getBody();
        claims.setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)); // Set to expire after 24 hours
        return Jwts.builder()
                .setClaims(claims)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Map<String,Long> decodeToken(String jwt){
        Claims claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(jwt).getBody();

        // Get the user ID and offer ID
        Long candidateId = Long.parseLong(claims.getSubject());
        Long offerId = claims.get("offerId", Long.class);

        Map<String, Long> decodedToken = new HashMap<>();

        decodedToken.put("offerId", offerId);
        decodedToken.put("candidateId", candidateId);

        return decodedToken;

    }




}
