package pl.com.betacom.task.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import pl.com.betacom.task.constants.API;
import pl.com.betacom.task.models.Item;
import pl.com.betacom.task.payload.requests.ItemRequest;
import pl.com.betacom.task.payload.response.ItemResponse;
import pl.com.betacom.task.services.ItemService;

@RestController
@RequestMapping(API.V1)
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/items")
    public ResponseEntity<Object> createItem(@RequestBody ItemRequest itemRequest) {
        itemService.addItem(new Item(itemRequest.getTitle()));
        return ResponseEntity.ok(null);
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemResponse>> getUserItems(@RequestHeader("Authorization") String authToken) {
        List<ItemResponse> items = itemService.getAllItems().stream()
                .map(item -> new ItemResponse(item.getId(), item.getTitle()) )
                .toList();
        return ResponseEntity.ok(items);
    }
}
