package pl.com.betacom.task.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.betacom.task.constants.API;
import pl.com.betacom.task.payload.requests.LoginRequest;
import pl.com.betacom.task.payload.requests.SignupRequest;
import pl.com.betacom.task.payload.response.MessageResponse;
import pl.com.betacom.task.payload.response.TokenResponse;
import pl.com.betacom.task.services.UserService;
import pl.com.betacom.task.token.JwtUtils;

@RestController
@RequestMapping(API.V1)
public class AuthController {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    public AuthController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        if (Boolean.TRUE.equals(userService.userExistsByLogin(loginRequest.getLogin()))) {
            String token = jwtUtils.generateTokenFromUsername(loginRequest.getLogin());
            return ResponseEntity.ok().body(new TokenResponse(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        userService.createUser(signUpRequest.getLogin(), signUpRequest.getPassword());
        return ResponseEntity.ok().body(new MessageResponse("User registered successfully!"));
    }
}