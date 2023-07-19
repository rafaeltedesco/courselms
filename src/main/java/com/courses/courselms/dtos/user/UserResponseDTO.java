package com.courses.courselms.dtos.user;

import com.courses.courselms.models.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserResponseDTO {
    private Long id;
    private String name;
    private String username;
    private String email;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.username = user.getUsername();
    }
}
