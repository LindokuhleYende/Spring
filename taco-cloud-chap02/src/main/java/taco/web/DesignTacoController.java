package taco.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import taco.Ingredient;
import taco.Ingredient.Type;
import taco.Taco;
import taco.TacoOrder;

import jakarta.validation.Valid;

import org.springframework.validation.Errors;

// You can use it for logging messages (e.g., log.info(), log.error()).
@Slf4j

// Marks this class as a Spring MVC Controller, making it capable of handling web requests.
@Controller

// Specifies that all request handler methods in this controller will handle requests
// that begin with "/design" (e.g., "/design", "/design/submit").
@RequestMapping("/design")

// Indicates that the 'tacoOrder' attribute should be stored in the session
// so it persists across multiple requests (e.g., from design to order pages).
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    // Adds a list of ingredients to the model before any request-handling method is invoked.
    // The model is a container that holds data to be rendered by the view (like an HTML page).
    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        // Create a hardcoded list of Ingredient objects to display on the design form.
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        // Get all possible ingredient types (WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE).
        Type[] types = Type.values();

        // For each type, add a filtered list of ingredients of that type to the model.
        // The key in the model (like "wrap" or "protein") will be used by the view (e.g., Thymeleaf).
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    // Creates a TacoOrder object and adds it to the model if it doesn't already exist in the session.
    // This ensures that each user has a TacoOrder while building tacos.
    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    // Creates a new Taco object to bind form data from the "design" page.
    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    // Handles GET requests to "/design".
    // Returns the name of the view ("design") to display the taco design form.
    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    // Handles POST requests to "/design" when the user submits the form.
    @PostMapping
    public String processTaco(
            @Valid Taco taco, Errors errors,
            @ModelAttribute TacoOrder tacoOrder) {

        // If form validation fails, redisplay the design form with error messages.
        if (errors.hasErrors()) {
            return "design";
        }

        // If valid, add the taco to the current TacoOrder.
        tacoOrder.addTaco(taco);

        // Log the taco data for debugging or tracking.
        log.info("Processing taco: {}", taco);

        // Redirect to the order form page after successfully creating a taco.
        return "redirect:/orders/current";
    }

    // Helper method that filters ingredients by their type.
    // Returns only ingredients that match the given type (e.g., all VEGGIES).
    private Iterable<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

}
