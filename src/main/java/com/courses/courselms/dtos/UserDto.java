package com.courses.courselms.dtos;

import com.courses.courselms.models.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private Boolean enabled = false;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.enabled = user.getEnabled();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    private UserDto(UserDtoBuilder dtoBuilder) {
        this.id = dtoBuilder.id;
        this.name = dtoBuilder.name;
        this.username = dtoBuilder.username;
        this.password = dtoBuilder.password;
        this.email = dtoBuilder.email;
        this.enabled = dtoBuilder.enabled;
    }
    public static class UserDtoBuilder {

        private Long id;
        private String name;
        private String username;
        private String password;
        private String email;
        private Boolean enabled;

        public UserDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public UserDtoBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserDtoBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public UserDtoBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserDtoBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserDtoBuilder setEnabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }

    }
}
