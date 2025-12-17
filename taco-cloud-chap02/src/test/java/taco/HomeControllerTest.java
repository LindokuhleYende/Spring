package taco;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

// Use @WebMvcTest to load only the web layer (controllers, filters, etc.)
// This does NOT start the full Spring Boot context
@WebMvcTest  // <1>
public class HomeControllerTest {

    // Inject MockMvc to simulate HTTP requests and assert responses
    @Autowired
    private MockMvc mockMvc;   // <2>

    @Test
    public void testHomePage() throws Exception {
        // Perform a GET request to the "/" URL
        mockMvc.perform(get("/"))    // <3>

                // Expect the HTTP response status to be 200 OK
                .andExpect(status().isOk())  // <4>

                // Expect the controller to return the "home" view
                .andExpect(view().name("home"))  // <5>

                // Expect the content of the response to contain the string "Welcome to..."
                .andExpect(content().string(
                        containsString("Welcome to...")));  // <6>
    }
}
