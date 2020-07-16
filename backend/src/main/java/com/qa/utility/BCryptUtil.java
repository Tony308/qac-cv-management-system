package com.qa.utility;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class BCryptUtil {

    private final BCryptPasswordEncoder encoder;

    public BCryptUtil() {
        encoder = new BCryptPasswordEncoder();
    }

    public String hashPassword(String password) {
        return encoder.encode(password);
    }

    public boolean verifyPwd(@NotNull String password, String hash) {

        try {
            return BCrypt.checkpw(password, hash);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}
