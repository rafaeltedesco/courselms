package com.courses.courselms;

import com.courses.courselms.dtos.user.UserPayloadDTO;
import com.courses.courselms.dtos.user.UserResponseDTO;
import com.courses.courselms.models.User;
import com.courses.courselms.repositories.UserRepository;
import com.courses.courselms.services.HtmlGenerator;
import com.courses.courselms.services.MailService;
import com.courses.courselms.services.UserServiceImp;
import com.courses.courselms.services.WelcomeHtmlGenerator;
import jakarta.persistence.Column;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MailService mailService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private HtmlGenerator htmlGenerator;

    private UserServiceImp userService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImp(userRepository, passwordEncoder, mailService, modelMapper, htmlGenerator);
    }
    @Test
    @DisplayName("Deve retornar usuário quando for válido")
    void deveRetornarUsuarioQuandoLoadByUsernameForChamadoComUsernameValido() {
        String username = "rafael";
        String password = "123456";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(user);
        UserDetails returnedUser = userService.loadUserByUsername(username);
        Assertions.assertEquals(returnedUser.getUsername(), username);
    }

    @Test()
    void deveLancarUmErrorAoProcurarUsuarioNaoCadastrado() {
        String invalidUsername = "miguel";
        String password = "123456";
        Mockito.when(userRepository.findByUsername(invalidUsername)).thenThrow(
                new UsernameNotFoundException(String.format("User %s not found", invalidUsername))
        );
        try {
            userService.loadUserByUsername(invalidUsername);
        } catch (UsernameNotFoundException ex) {
            Assertions.assertEquals(String.format("User %s not found", invalidUsername), ex.getMessage());
        }

        Mockito.verify(userRepository).findByUsername("miguel");

    }

    @Test
    @DisplayName("Deve retornar o DTO de todos usuários")
    void shouldReturnUsersDTO() {
        User user = new User();
        user.setId(1l);
        user.setUsername("rafael");
        user.setName("rafael tedesco");
        user.setPassword("123456");
        user.setEmail("rafael@tedesco.com");
        List<User> users = new ArrayList<>() {
            {
                add(user);
            }
        };

        Mockito.when(userRepository.findAll()).thenReturn(users);

       List<UserResponseDTO> usersResponseDto = userService.findAll();

        List<UserResponseDTO> expectedUsersDTO = new ArrayList<>() {
            {
                add(new UserResponseDTO(user));
            }
        };

       Assertions.assertArrayEquals(expectedUsersDTO.toArray(), usersResponseDto.toArray());
    }

    @Test
    @DisplayName("Deve criar Usuário")
    void shouldCreateUser() {
        UserPayloadDTO payload = new UserPayloadDTO("rafael", "rafael", "123456", "rafael@tedesco.com");
        User user = new User();
        user.setName("rafael");
        user.setUsername("rafael");
        user.setPassword("123456");
        user.setEmail("rafael@tedesco.com");
        Mockito.when(modelMapper.map(payload, User.class)).thenReturn(user);

        User userWithId = new User(1l, "rafael", "rafael", "123456", "rafael@tedesco.com", false, "12345678910" );
        Mockito.when(userRepository.save(user)).thenReturn(userWithId);
        Mockito.when(htmlGenerator.generate(Mockito.anyString())).thenReturn("meu template html");
        UserResponseDTO response = userService.create(payload);

        UserResponseDTO expectedResponse = new UserResponseDTO(1l, "rafael", "rafael", "rafael@tedesco.com");
        Assertions.assertEquals(expectedResponse, response);
        Mockito.verify(mailService).sendMail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }
}
