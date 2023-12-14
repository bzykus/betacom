package pl.com.betacom.task.services;

import org.springframework.stereotype.Service;
import java.util.List;
import pl.com.betacom.task.models.Item;
import pl.com.betacom.task.repositories.ItemRepository;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    public void addItem(Item item) {
        itemRepository.save(item);
    }

}
