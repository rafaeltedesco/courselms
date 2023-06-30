package com.courses.courselms.services;

import com.courses.courselms.dtos.UserDto;
import com.courses.courselms.models.User;
import com.courses.courselms.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        return user;
    }

    public List<UserDto> findAll() {
        return this.userRepository.findAll().stream().map(user ->
                        new UserDto.UserDtoBuilder()
                                .setId(user.getId())
                                .setName(user.getName())
                                .setUsername(user.getUsername())
                                .setEnabled(user.getEnabled())
                                .build()
                ).toList();
    }

    public UserDto create(UserDto userDto) {
        User newUser = modelMapper.map(userDto, User.class);
        String hashedPassword = this.bCryptPasswordEncoder.encode(newUser.getPassword());
        newUser.setPassword(hashedPassword);
        User user = this.userRepository.save(newUser);
        return new UserDto.UserDtoBuilder()
                .setId(user.getId())
                .setName(user.getName())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .build();
    }
}