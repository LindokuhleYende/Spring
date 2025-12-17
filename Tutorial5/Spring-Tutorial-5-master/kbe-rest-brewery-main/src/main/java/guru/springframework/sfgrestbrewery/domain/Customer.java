package guru.springframework.sfgrestbrewery.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Simple Customer model.
 * This is NOT a database entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    private UUID id;    // Unique ID for the customer
    private String name; // Customer's name
}