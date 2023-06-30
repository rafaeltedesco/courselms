package com.courses.courselms.dtos.user;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class UserPayloadDTO {
    private String name;
    private String username;
    private String password;
    private String email;
}
