package guru.springframework.sfgrestbrewery.repositories;

import guru.springframework.sfgrestbrewery.domain.Beer;
import guru.springframework.sfgrestbrewery.web.model.BeerStyleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * BeerRepository is the Data Access Layer for Beer entities.
 * It gives CRUD operations automatically (thanks to JpaRepository)
 * and also includes custom query methods based on method names.
 */
public interface BeerRepository extends JpaRepository<Beer, UUID> {

    /**
     * Finds beers by exact beer name, and returns paginated results.
     * Example:
     *   findAllByBeerName("Corona", pageable)
     */
    Page<Beer> findAllByBeerName(String beerName, Pageable pageable);

    /**
     * Finds beers by style (IPA, LAGER, etc.) with pagination support.
     */
    Page<Beer> findAllByBeerStyle(BeerStyleEnum beerStyle, Pageable pageable);

    /**
     * Finds beers matching both name AND style.
     * Uses pagination.
     */
    Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyleEnum beerStyle, Pageable pageable);

    /**
     * Finds a beer using its UPC (Unique Product Code).
     * Returns a single beer, not a page.
     */
    Beer findByUpc(String upc);
}