package com.courses.courselms.controllers;

import com.courses.courselms.dtos.UserDto;
import com.courses.courselms.services.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserServiceImp userServiceImp;

    @PostMapping
    public UserDto create(@RequestBody UserDto userDto) {
        return this.userServiceImp.create(userDto);
    }

    @GetMapping
    public List<UserDto> findAll() {
        return this.userServiceImp.findAll();
    }

}
