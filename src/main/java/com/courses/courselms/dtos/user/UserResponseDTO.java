package com.courses.courselms.dtos.user;

import com.courses.courselms.models.User;

public record UserResponseDTO(Long id, String username, String email, String name) {

    public UserResponseDTO(User user) {
        this(user.getId(), user.getUsername(), user.getEmail(), user.getName());
    }
}
