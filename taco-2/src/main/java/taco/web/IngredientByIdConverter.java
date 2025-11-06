package taco.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import taco.Ingredient;
import taco.Ingredient.Type;

@Component
// This class converts a String (ingredient ID) into an Ingredient object.
// It is typically used by Spring when binding form data to model objects.
// For example, when a user selects ingredients in a form, the submitted
// ingredient IDs (as Strings) are automatically converted into Ingredient objects.

public class IngredientByIdConverter implements Converter<String, Ingredient> {

    // A simple in-memory map that holds Ingredient objects,
    // keyed by their unique String IDs (e.g., "FLTO", "COTO").
    private Map<String, Ingredient> ingredientMap = new HashMap<>();

    // Constructor initializes the ingredientMap with sample data.
    // Each ingredient is added with an ID, name, and type.
    public IngredientByIdConverter() {
        ingredientMap.put("FLTO",
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        ingredientMap.put("COTO",
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
        ingredientMap.put("GRBF",
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
        ingredientMap.put("CARN",
                new Ingredient("CARN", "Carnitas", Type.PROTEIN));
        ingredientMap.put("TMTO",
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
        ingredientMap.put("LETC",
                new Ingredient("LETC", "Lettuce", Type.VEGGIES));
        ingredientMap.put("CHED",
                new Ingredient("CHED", "Cheddar", Type.CHEESE));
        ingredientMap.put("JACK",
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
        ingredientMap.put("SLSA",
                new Ingredient("SLSA", "Salsa", Type.SAUCE));
        ingredientMap.put("SRCR",
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
    }

    // The convert() method is automatically called by Spring
    // when it needs to convert a String (such as a form input value)
    // into an Ingredient object.
    //
    // For example, if a form sends "GRBF" as a selected ingredient ID,
    // this method will return the corresponding Ingredient object.
    @Override
    public Ingredient convert(String id) {
        return ingredientMap.get(id);
    }
}
