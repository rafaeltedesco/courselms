package com.courses.courselms.services;

import com.courses.courselms.dtos.user.UserPayloadDTO;
import com.courses.courselms.dtos.user.UserResponseDTO;
import com.courses.courselms.models.User;
import com.courses.courselms.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        return user;
    }

    public List<UserResponseDTO> findAll() {
        return this.userRepository.findAll().stream().map(UserResponseDTO::new).toList();
    }

    public UserResponseDTO create(UserPayloadDTO userDto) {
        User newUser = modelMapper.map(userDto, User.class);
        String hashedPassword = this.passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(hashedPassword);
        User user = this.userRepository.save(newUser);
        return new UserResponseDTO(user);
    }
}