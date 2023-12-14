package pl.com.betacom.task.payload.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupRequest {
    private String login;
    private String password;
}
