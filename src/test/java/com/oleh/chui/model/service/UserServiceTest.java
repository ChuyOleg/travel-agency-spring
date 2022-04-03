package com.oleh.chui.model.service;

import com.oleh.chui.model.dto.UserDto;
import com.oleh.chui.model.entity.Role;
import com.oleh.chui.model.entity.User;
import com.oleh.chui.model.exception.user.UsernameIsReservedException;
import com.oleh.chui.model.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleService roleService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    private static final String USERNAME = "Oleh";
    private static final String PASSWORD = "SUPER_PASSWORD_31";
    private static final String ENCODED_PASSWORD = "ENCODED_PASSWORD";

    private static final UserDto USER_DTO = new UserDto(
            USERNAME,
            PASSWORD,
            PASSWORD,
            "FIRST_NAME",
            "LAST_NAME",
            "EMAIL"
    );

    private static final User USER_FROM_USER_DTO = new User(USER_DTO);

    private static final Role UserRole = new Role(1L, Role.RoleEnum.USER);

    @Test
    void checkRegisterNewAccountShouldThrowUsernameIsReservedException() {
        when(userRepository.existsByUsername(USERNAME)).thenReturn(true);

        assertThrows(
                UsernameIsReservedException.class,
                () -> userService.registerNewAccount(USER_DTO)
        );
    }

    @Test
    void checkRegisterNewAccountShouldWorkWithoutException() {
        when(userRepository.existsByUsername(USERNAME)).thenReturn(false);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(roleService.findByValue(Role.RoleEnum.USER)).thenReturn(UserRole);

        USER_FROM_USER_DTO.setPassword(ENCODED_PASSWORD);
        USER_FROM_USER_DTO.setRole(UserRole);

        assertDoesNotThrow(() -> userService.registerNewAccount(USER_DTO));

        verify(userRepository, times(1)).save(USER_FROM_USER_DTO);
    }

}
