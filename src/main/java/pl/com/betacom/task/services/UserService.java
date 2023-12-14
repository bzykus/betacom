package pl.com.betacom.task.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.com.betacom.task.exceptions.ItemAlreadyExistsException;
import pl.com.betacom.task.models.User;
import pl.com.betacom.task.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public boolean userExistsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    public void createUser(String login, String password) {
        if (Boolean.TRUE.equals(userRepository.existsByLogin(login))) {
            throw new ItemAlreadyExistsException();
        }
        User user = new User(login, passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
