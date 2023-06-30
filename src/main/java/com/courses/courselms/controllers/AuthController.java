package com.courses.courselms.controllers;

import com.courses.courselms.dtos.auth.LoginPayloadDTO;
import com.courses.courselms.models.User;
import com.courses.courselms.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginPayloadDTO loginDto) {
        Authentication auth = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password()));
        String token = jwtService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new HashMap<>() {{
            put("token", token);
        }});

    }
}
