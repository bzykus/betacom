package pl.com.betacom.task.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Entity
@Data
@Table(name = "items",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")
        })
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @NotBlank
    @Size(max = 255)
    String title;

    public Item(String title) {
        this.title = title;
    }
}
