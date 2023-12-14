package pl.com.betacom.task.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ItemResponse {
    UUID id;
    String title;
}
