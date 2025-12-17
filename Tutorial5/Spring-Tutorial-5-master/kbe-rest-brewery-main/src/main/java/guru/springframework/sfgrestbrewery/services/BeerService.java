package guru.springframework.sfgrestbrewery.services;

import guru.springframework.sfgrestbrewery.web.model.BeerDto;
import guru.springframework.sfgrestbrewery.web.model.BeerPagedList;
import guru.springframework.sfgrestbrewery.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

/**
 * Service layer for beer operations.
 */
public interface BeerService {

    // List beers with optional filters and pagination.
    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);

    // Get a beer by its ID (option to show inventory).
    BeerDto getById(UUID beerId, Boolean showInventoryOnHand);

    // Create a new beer.
    BeerDto saveNewBeer(BeerDto beerDto);

    // Update an existing beer.
    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    // Find a beer using its UPC.
    BeerDto getByUpc(String upc);

    // Delete a beer by ID.
    void deleteBeerById(UUID beerId);
}
