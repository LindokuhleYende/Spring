package guru.springframework.sfgrestbrewery.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

/**
 * Created by jt on 2019-04-21.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    private UUID id;  // Unique customer ID

    @NotBlank                // Name cannot be empty
    @Size(min = 3, max = 100) // Name must be between 3 and 100 characters
    private String name;      // Customer's full name
}
