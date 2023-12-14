package pl.com.betacom.task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.betacom.task.models.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>
{
}
