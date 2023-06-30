package com.courses.courselms.services.adapters;

import com.courses.courselms.services.ports.IHashEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

public class BcryptAdapter implements IHashEncoder {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Override
    public String encode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
