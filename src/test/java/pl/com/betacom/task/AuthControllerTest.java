package pl.com.betacom.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import pl.com.betacom.task.controllers.AuthController;
import pl.com.betacom.task.payload.requests.SignupRequest;
import pl.com.betacom.task.payload.response.MessageResponse;
import pl.com.betacom.task.services.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

        @Test
        void registerUser_ValidSignupRequest_ReturnsMessageResponse() {
            SignupRequest signupRequest = new SignupRequest("newUsername", "password");

            ResponseEntity<MessageResponse> response = authController.registerUser(signupRequest);

            ResponseEntity<MessageResponse> expectedResponse = ResponseEntity.ok(new MessageResponse("User registered successfully!"));
            assertEquals(expectedResponse, response);
        }

}
