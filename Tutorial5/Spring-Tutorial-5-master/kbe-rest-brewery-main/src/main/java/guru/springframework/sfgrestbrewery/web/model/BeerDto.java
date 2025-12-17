package guru.springframework.sfgrestbrewery.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Created by jt on 2019-04-20.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto {

    @Null  // ID must be null when creating a new beer
    private UUID id;

    @NotBlank  // Beer name cannot be empty
    private String beerName;

    @NotBlank  // Beer style cannot be empty
    private String beerStyle;

    private String upc;  // Product UPC code

    private BigDecimal price;  // Beer price

    private Integer quantityOnHand;  // Current stock

    private OffsetDateTime createdDate;  // When beer was created
    private OffsetDateTime lastUpdatedDate;  // When beer was last updated
}