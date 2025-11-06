package taco;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

// Use Spring Boot testing support with a random port for web environment
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DesignAndOrderTacosBrowserTest {

    // Browser instance for simulating user interactions
    private static HtmlUnitDriver browser;

    // Inject the randomly assigned port for the running server
    @LocalServerPort
    private int port;

    // For performing REST calls if needed (not used directly in this class)
    @Autowired
    TestRestTemplate rest;

    // -------------------------
    // Test setup and teardown
    // -------------------------

    @BeforeAll
    public static void setup() {
        // Initialize headless browser for end-to-end tests
        browser = new HtmlUnitDriver();
        browser.manage().timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS); // wait up to 10s for elements
    }

    @AfterAll
    public static void closeBrowser() {
        // Close browser after all tests
        browser.quit();
    }

    // -------------------------
    // Test cases
    // -------------------------

    @Test
    public void testDesignATacoPage_HappyPath() throws Exception {
        // Full happy path: design multiple tacos and submit order
        browser.get(homePageUrl());
        clickDesignATaco();
        assertDesignPageElements();

        // Build and submit first taco
        buildAndSubmitATaco("Basic Taco", "FLTO", "GRBF", "CHED", "TMTO", "SLSA");

        // Build and submit another taco
        clickBuildAnotherTaco();
        buildAndSubmitATaco("Another Taco", "COTO", "CARN", "JACK", "LETC", "SRCR");

        // Fill order form and submit
        fillInAndSubmitOrderForm();

        // Ensure we return to homepage after successful submission
        assertThat(browser.getCurrentUrl()).isEqualTo(homePageUrl());
    }

    @Test
    public void testDesignATacoPage_EmptyOrderInfo() throws Exception {
        // Test submitting an order with empty required fields
        browser.get(homePageUrl());
        clickDesignATaco();
        assertDesignPageElements();

        buildAndSubmitATaco("Basic Taco", "FLTO", "GRBF", "CHED", "TMTO", "SLSA");

        // Attempt to submit empty order form
        submitEmptyOrderForm();

        // Fill valid information and submit successfully
        fillInAndSubmitOrderForm();

        // Verify we are back at the homepage
        assertThat(browser.getCurrentUrl()).isEqualTo(homePageUrl());
    }

    @Test
    public void testDesignATacoPage_InvalidOrderInfo() throws Exception {
        // Test submitting an order with invalid data (e.g., invalid credit card)
        browser.get(homePageUrl());
        clickDesignATaco();
        assertDesignPageElements();

        buildAndSubmitATaco("Basic Taco", "FLTO", "GRBF", "CHED", "TMTO", "SLSA");

        // Submit order with invalid info
        submitInvalidOrderForm();

        // Fill valid information and submit successfully
        fillInAndSubmitOrderForm();

        assertThat(browser.getCurrentUrl()).isEqualTo(homePageUrl());
    }

    // -------------------------
    // Browser interaction helpers
    // -------------------------

    /** Select ingredients, name taco, and submit design form */
    private void buildAndSubmitATaco(String name, String... ingredients) {
        assertDesignPageElements();

        for (String ingredient : ingredients) {
            browser.findElementByCssSelector("input[value='" + ingredient + "']").click();
        }
        browser.findElementByCssSelector("input#name").sendKeys(name);
        browser.findElementByCssSelector("form").submit();
    }

    /** Assert that all expected ingredient groups and ingredients are present */
    private void assertDesignPageElements() {
        assertThat(browser.getCurrentUrl()).isEqualTo(designPageUrl());

        List<WebElement> ingredientGroups = browser.findElementsByClassName("ingredient-group");
        assertThat(ingredientGroups.size()).isEqualTo(5);

        // Check wraps
        WebElement wrapGroup = browser.findElementByCssSelector("div.ingredient-group#wraps");
        assertIngredient(wrapGroup, 0, "FLTO", "Flour Tortilla");
        assertIngredient(wrapGroup, 1, "COTO", "Corn Tortilla");

        // Check proteins
        WebElement proteinGroup = browser.findElementByCssSelector("div.ingredient-group#proteins");
        assertIngredient(proteinGroup, 0, "GRBF", "Ground Beef");
        assertIngredient(proteinGroup, 1, "CARN", "Carnitas");

        // Check cheeses
        WebElement cheeseGroup = browser.findElementByCssSelector("div.ingredient-group#cheeses");
        assertIngredient(cheeseGroup, 0, "CHED", "Cheddar");
        assertIngredient(cheeseGroup, 1, "JACK", "Monterrey Jack");

        // Check veggies
        WebElement veggieGroup = browser.findElementByCssSelector("div.ingredient-group#veggies");
        assertIngredient(veggieGroup, 0, "TMTO", "Diced Tomatoes");
        assertIngredient(veggieGroup, 1, "LETC", "Lettuce");

        // Check sauces
        WebElement sauceGroup = browser.findElementByCssSelector("div.ingredient-group#sauces");
        assertIngredient(sauceGroup, 0, "SLSA", "Salsa");
        assertIngredient(sauceGroup, 1, "SRCR", "Sour Cream");
    }

    /** Fill all order fields with valid data and submit */
    private void fillInAndSubmitOrderForm() {
        assertThat(browser.getCurrentUrl()).startsWith(orderDetailsPageUrl());

        fillField("input#deliveryName", "Ima Hungry");
        fillField("input#deliveryStreet", "1234 Culinary Blvd.");
        fillField("input#deliveryCity", "Foodsville");
        fillField("input#deliveryState", "CO");
        fillField("input#deliveryZip", "81019");
        fillField("input#ccNumber", "4111111111111111");
        fillField("input#ccExpiration", "10/23");
        fillField("input#ccCVV", "123");
        browser.findElementByCssSelector("form").submit();
    }

    /** Submit an empty order form to test validation messages */
    private void submitEmptyOrderForm() {
        assertThat(browser.getCurrentUrl()).isEqualTo(currentOrderDetailsPageUrl());
        browser.findElementByCssSelector("form").submit();

        assertThat(browser.getCurrentUrl()).isEqualTo(orderDetailsPageUrl());

        List<String> validationErrors = getValidationErrorTexts();
        assertThat(validationErrors.size()).isEqualTo(9);
        assertThat(validationErrors).containsExactlyInAnyOrder(
                "Please correct the problems below and resubmit.",
                "Delivery name is required",
                "Street is required",
                "City is required",
                "State is required",
                "Zip code is required",
                "Not a valid credit card number",
                "Must be formatted MM/YY",
                "Invalid CVV"
        );
    }

    /** Submit order form with invalid data to test validation errors */
    private void submitInvalidOrderForm() {
        assertThat(browser.getCurrentUrl()).startsWith(orderDetailsPageUrl());

        fillField("input#deliveryName", "I");
        fillField("input#deliveryStreet", "1");
        fillField("input#deliveryCity", "F");
        fillField("input#deliveryState", "C");
        fillField("input#deliveryZip", "8");
        fillField("input#ccNumber", "1234432112344322");
        fillField("input#ccExpiration", "14/91");
        fillField("input#ccCVV", "1234");
        browser.findElementByCssSelector("form").submit();

        assertThat(browser.getCurrentUrl()).isEqualTo(orderDetailsPageUrl());

        List<String> validationErrors = getValidationErrorTexts();
        assertThat(validationErrors.size()).isEqualTo(4);
        assertThat(validationErrors).containsExactlyInAnyOrder(
                "Please correct the problems below and resubmit.",
                "Not a valid credit card number",
                "Must be formatted MM/YY",
                "Invalid CVV"
        );
    }

    /** Helper to get all visible validation error texts */
    private List<String> getValidationErrorTexts() {
        List<WebElement> validationErrorElements = browser.findElementsByClassName("validationError");
        return validationErrorElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /** Helper to fill a single input field */
    private void fillField(String fieldName, String value) {
        WebElement field = browser.findElementByCssSelector(fieldName);
        field.clear();
        field.sendKeys(value);
    }

    /** Assert that a single ingredient within a group has the expected id and name */
    private void assertIngredient(WebElement ingredientGroup, int ingredientIdx, String id, String name) {
        List<WebElement> ingredients = ingredientGroup.findElements(By.tagName("div"));
        WebElement ingredient = ingredients.get(ingredientIdx);

        assertThat(ingredient.findElement(By.tagName("input")).getAttribute("value")).isEqualTo(id);
        assertThat(ingredient.findElement(By.tagName("span")).getText()).isEqualTo(name);
    }

    /** Click the "Design a Taco" link from the homepage */
    private void clickDesignATaco() {
        assertThat(browser.getCurrentUrl()).isEqualTo(homePageUrl());
        browser.findElementByCssSelector("a[id='design']").click();
    }

    /** Click the "Build Another Taco" link from the order form */
    private void clickBuildAnotherTaco() {
        assertThat(browser.getCurrentUrl()).startsWith(orderDetailsPageUrl());
        browser.findElementByCssSelector("a[id='another']").click();
    }

    // -------------------------
    // URL helper methods
    // -------------------------

    private String designPageUrl() {
        return homePageUrl() + "design";
    }

    private String homePageUrl() {
        return "http://localhost:" + port + "/";
    }

    private String orderDetailsPageUrl() {
        return homePageUrl() + "orders";
    }

    private String currentOrderDetailsPageUrl() {
        return homePageUrl() + "orders/current";
    }
}

