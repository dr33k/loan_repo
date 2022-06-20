package com.agicomputers.LoanAPI.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.LocalDate;
import java.sql.Date;

@Configuration
public class SecurityConstants {
    public static final String KEY = "398hhf984h93h9rh3ihni4r98on3i74hoskh3i47hi3hb4uyh9hs9h487hughs87h4i8h3kuks4y7gf48o37h8o0o0znbzkzb64t7a9983h8ga837gh83brjvn8s7hr78z6g76hgvVhvhc7ggv7v7vctrCccdn88g834hn3nr8378ha7g38b7a67ffad5s33s4333577ag8egiuauyvjahgf87h8ff7gh84374g76g87igsuj6grb78g76g475f7grg746g4g76tgsbiu4g";
    public static final String PREFIX = "Bearer ";
    public static final KeyPair KEY_PAIR = generator();
    public static Key ENCRYPTING_KEY = KEY_PAIR.getPrivate();
    public static Key DECRYPTING_KEY = KEY_PAIR.getPublic();
    public static final Date EXPIRY = Date.valueOf(LocalDate.now().plusWeeks(2));

    @Autowired
    Environment env;

    public static KeyPair generator(){
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048, new SecureRandom(KEY.getBytes(StandardCharsets.UTF_8)));
            KeyPair keyPair = generator.generateKeyPair();
            return keyPair;
        }catch(NoSuchAlgorithmException ex){ return Keys.keyPairFor(SignatureAlgorithm.RS256);}
    }
    }
