package com.courses.courselms.controllers;

import com.courses.courselms.dtos.auth.LoginPayloadDTO;
import com.courses.courselms.dtos.auth.TokenResponseDTO;
import com.courses.courselms.models.User;
import com.courses.courselms.services.JwtService;
import com.courses.courselms.services.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImp userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("login")
    public TokenResponseDTO login(@RequestBody LoginPayloadDTO loginDTO) {
        Authentication auth = new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());
        Authentication authenticated = authenticationManager.authenticate(auth);
        String token = jwtService.generateToken((User) authenticated.getPrincipal());
        return new TokenResponseDTO(token);
    }

    @GetMapping("activation/{activationCode}")
    public HashMap<String, Object> activateUser(@PathVariable String activationCode) {
        this.userService.activateUser(activationCode);
        return new HashMap<>() {
            {
                put("message", "user activated");
            }
        };
    }
}
