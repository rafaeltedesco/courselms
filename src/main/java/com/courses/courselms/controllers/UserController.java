package com.courses.courselms.controllers;

import com.courses.courselms.dtos.user.UserPayloadDTO;
import com.courses.courselms.dtos.user.UserResponseDTO;
import com.courses.courselms.services.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserServiceImp userServiceImp;

    @PostMapping
    public UserResponseDTO create(@RequestBody UserPayloadDTO userDto) {
        return this.userServiceImp.create(userDto);
    }

    @GetMapping
    public List<UserResponseDTO> findAll() {
        return this.userServiceImp.findAll();
    }

}
