package guru.springframework.sfgrestbrewery.web.mappers;

import guru.springframework.sfgrestbrewery.domain.Customer;
import guru.springframework.sfgrestbrewery.web.model.CustomerDto;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for converting between Customer and CustomerDto.
 */
@Mapper
public interface CustomerMapper {

    // Convert DTO → entity
    Customer customerDtoToCustomer(CustomerDto dto);

    // Convert entity → DTO
    CustomerDto customerToCustomerDto(Customer customer);
}
