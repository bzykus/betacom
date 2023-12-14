package pl.com.betacom.task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.betacom.task.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    Boolean existsByLogin(String login);
}
