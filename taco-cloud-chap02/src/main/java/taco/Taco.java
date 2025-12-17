package taco;

import java.util.List;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class Taco {

    // Validation ensures it is not null and has at least 5 characters.
    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    // A list of ingredients used in the taco.
    // Validation ensures it is not null and contains at least one ingredient.
    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;

}
