package taco;

import java.util.concurrent.TimeUnit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// Integration test that launches the Taco Cloud application and uses a
// headless browser (HtmlUnit) to verify the home page content and behavior.

@ExtendWith(SpringExtension.class) // <1>
// Integrates the Spring TestContext Framework with JUnit 5.
// Enables support for dependency injection, Spring Boot context loading, etc.

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// Loads the full Spring Boot application context for testing.
// The web environment starts an embedded server (Tomcat) on a random port,
// allowing real HTTP requests to be made against the running app.

public class HomePageBrowserTest {

    // Injects the actual random port number that Spring Boot assigned
    // to the embedded server when it started for this test.
    @LocalServerPort
    private int port;

    // A static instance of HtmlUnitDriver, a headless browser provided by Selenium.
    // It can load pages and interact with them programmatically without showing a GUI.
    private static HtmlUnitDriver browser;

    // -----------------------------------------------------
    // Runs once before all tests in this class.
    // Sets up the browser with a default timeout configuration.
    // -----------------------------------------------------
    @BeforeAll
    public static void setup() {
        browser = new HtmlUnitDriver();  // Create headless browser instance

        // Configure implicit wait: the browser will wait up to 10 seconds
        // when searching for elements before throwing an exception.
        browser.manage().timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }

    // -----------------------------------------------------
    // Runs once after all tests are completed.
    // Cleans up resources and closes the browser instance.
    // -----------------------------------------------------
    @AfterAll
    public static void teardown() {
        browser.quit();
    }

    // -----------------------------------------------------
    // Test method: verifies that the Taco Cloud home page is loaded correctly.
    // -----------------------------------------------------
    @Test
    public void testHomePage() {
        // Build the URL of the home page using the injected random port
        String homePage = "http://localhost:" + port;

        // Instruct the browser to navigate to the home page
        browser.get(homePage);

        // Verify that the <title> tag text is "Taco Cloud"
        String titleText = browser.getTitle();
        Assertions.assertThat(titleText)
                .isEqualTo("Taco Cloud");

        // Verify that the main heading <h1> text is "Welcome to..."
        String h1Text = browser
                .findElementByTagName("h1")
                .getText();
        Assertions.assertThat(h1Text)
                .isEqualTo("Welcome to...");

        // Verify that the <img> tag’s 'src' attribute points to the Taco Cloud logo
        String imgSrc = browser
                .findElementByTagName("img")
                .getAttribute("src");
        Assertions.assertThat(imgSrc)
                .isEqualTo(homePage + "/images/TacoCloud.png");
    }
  
  
}
