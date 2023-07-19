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
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserDetailsService {


    private static String subject = "Seu código de ativação!";
    private UserRepository userRepository;

    private HtmlGenerator htmlGenerator;


    private ModelMapper modelMapper;


    private MailService mailService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(
            UserRepository repository,
            PasswordEncoder encoder,
            MailService mailService,
            ModelMapper modelMapper,
            HtmlGenerator htmlGenerator
    ) {
        this.userRepository = repository;
        this.passwordEncoder = encoder;
        this.mailService = mailService;
        this.modelMapper = modelMapper;
        this.htmlGenerator = htmlGenerator;
    }

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
        String activationCode = "123456";
        newUser.setActivationCode(activationCode);
        User user = this.userRepository.save(newUser);
        this.mailService.sendMail(userDto.getEmail(), UserServiceImp.subject, this.htmlGenerator.generate(activationCode));
        return new UserResponseDTO(user);
    }

    public void activateUser(String activationCode) {
        User user = this.userRepository.findByActivationCode(activationCode)
                .orElseThrow(() -> new RuntimeException("invalid activation code"));
        user.setEnabled(true);
        user.setActivationCode(null);
        this.userRepository.save(user);
    }
}