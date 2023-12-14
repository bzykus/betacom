package pl.com.betacom.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import pl.com.betacom.task.exceptions.ItemAlreadyExistsException;
import pl.com.betacom.task.models.User;
import pl.com.betacom.task.repositories.UserRepository;
import pl.com.betacom.task.services.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserServiceTests {
    @Spy
    private UserRepository userRepository;

    @Spy
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void userExistsByLogin_UserExists_ReturnsTrue() {
        String login = "existingUser";
        when(userRepository.existsByLogin(login)).thenReturn(true);

        boolean result = userService.userExistsByLogin(login);

        assertTrue(result);
        verify(userRepository, times(1)).existsByLogin(login);
    }

    @Test
    void userExistsByLogin_UserDoesNotExist_ReturnsFalse() {
        String login = "nonExistingUser";
        boolean result = userService.userExistsByLogin(login);
        assertFalse(result);
    }

    @Test
    void createUser_NewUser_CreatesUserSuccessfully() {
        String login = "newUser";
        String password = "password";
        when(userRepository.existsByLogin(login)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        userService.createUser(login, password);

        verify(userRepository, times(1)).existsByLogin(login);
        verify(passwordEncoder, times(1)).encode(password);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_DuplicateUser_ThrowsItemAlreadyExistsException() {
        String login = "existingUser";
        String password = "password";
        when(userRepository.existsByLogin(login)).thenReturn(true);

        assertThrows(ItemAlreadyExistsException.class, () -> userService.createUser(login, password));
        verify(userRepository, times(1)).existsByLogin(login);
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}
