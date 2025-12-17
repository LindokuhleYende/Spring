package guru.springframework.sfgrestbrewery.services;

import guru.springframework.sfgrestbrewery.web.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by jt on 2019-04-21.
 */
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    /**
     * Return a dummy customer (hard-coded).
     * In a real app, this would query the database.
     */
    @Override
    public CustomerDto getCustomerById(UUID customerId) {
        return CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("Joe Buck")
                .build();
    }

    /**
     * Save a new customer (currently returns a random ID).
     * Real implementation would persist it to DB.
     */
    @Override
    public CustomerDto saveNewCustomer(CustomerDto customerDto) {
        return CustomerDto.builder()
                .id(UUID.randomUUID())
                .build();
    }

    /**
     * Update a customer — not implemented yet.
     */
    @Override
    public void updateCustomer(UUID customerId, CustomerDto customerDto) {
        log.debug("Updating....");
    }

    /**
     * Delete a customer — not implemented yet.
     */
    @Override
    public void deleteById(UUID customerId) {
        log.debug("Deleting.... ");
    }
}
