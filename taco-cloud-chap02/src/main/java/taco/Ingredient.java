package taco;

import lombok.Data;

// Lombok annotation that automatically generates boilerplate code such as
// getters, setters, toString(), equals(), and hashCode() methods at compile time.
@Data
public class Ingredient {

    // Unique identifier for the ingredient
    private final String id;

    // Descriptive name of the ingredient (e.g., "Flour Tortilla", "Chicken", "Lettuce")
    private final String name;

    // Category/type of the ingredient (e.g., wrap, protein, veggies, etc.)
    private final Type type;

    // Enum representing the possible ingredient categories
    public enum Type {
        WRAP,     // Used to wrap the ingredients (e.g., tortilla)
        PROTEIN,  // Protein source (e.g., chicken, beef, tofu)
        VEGGIES,  // Vegetables (e.g., lettuce, tomato)
        CHEESE,   // Cheese types (e.g., cheddar, mozzarella)
        SAUCE     // Sauces or condiments (e.g., salsa, mayo)
    }

}

