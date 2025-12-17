package guru.springframework.sfgrestbrewery.services;

import guru.springframework.sfgrestbrewery.web.model.CustomerDto;

import java.util.UUID;

/**
 * Service interface for customer operations.
 */
public interface CustomerService {

    // Get a single customer by ID.
    CustomerDto getCustomerById(UUID customerId);

    // Create a new customer.
    CustomerDto saveNewCustomer(CustomerDto customerDto);

    // Update an existing customer.
    void updateCustomer(UUID customerId, CustomerDto customerDto);

    // Delete a customer by ID.
    void deleteById(UUID customerId);
}
