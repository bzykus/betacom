package pl.com.betacom.task.payload.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    private String login;
    private String password;
}
