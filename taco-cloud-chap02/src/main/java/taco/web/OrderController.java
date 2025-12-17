package taco.web;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import taco.TacoOrder;

@Slf4j
@Controller
@RequestMapping("/orders")               // Base URL for all request mappings in this controller (e.g., "/orders/current", "/orders")
@SessionAttributes("tacoOrder")          // Keeps the 'tacoOrder' object in session across multiple requests
public class OrderController {

    // Displays the order form page where the user can enter delivery and payment details.
    // The view returned ("orderForm") corresponds to orderForm.html (the Thymeleaf template).
    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    // Processes the submitted order form when the user clicks "Submit Order".
    // - @Valid automatically triggers validation on the TacoOrder object based on its constraints.
    // - Errors holds any validation errors that occur during form submission.
    // - SessionStatus is used to clear session attributes after the order is complete.
    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors,
                               SessionStatus sessionStatus) {

        // If validation errors exist, re-display the form with error messages.
        if (errors.hasErrors()) {
            return "orderForm";
        }

        // Log the submitted order details for debugging or tracking purposes.
        log.info("Order submitted: {}", order);

        // Mark the session as complete — removes 'tacoOrder' from the session
        // to avoid stale data after a successful order submission.
        sessionStatus.setComplete();

        // Redirect the user to the homepage after successful order submission.
        // The "redirect:/" prefix tells Spring to perform a client-side redirect.
        return "redirect:/";
    }
}
