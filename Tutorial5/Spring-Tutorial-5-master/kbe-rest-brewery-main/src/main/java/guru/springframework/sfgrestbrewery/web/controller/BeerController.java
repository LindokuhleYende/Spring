package guru.springframework.sfgrestbrewery.web.controller;

import guru.springframework.sfgrestbrewery.services.BeerService;
import guru.springframework.sfgrestbrewery.web.model.BeerDto;
import guru.springframework.sfgrestbrewery.web.model.BeerPagedList;
import guru.springframework.sfgrestbrewery.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

/**
 * Created by jt on 2019-04-20.
 */
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@RestController
public class BeerController {

    // Default values for pagination
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    // Injecting BeerService
    private final BeerService beerService;

    @GetMapping(produces = { "application/json" }, path = "beer")
    public ResponseEntity<BeerPagedList> listBeers(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "beerName", required = false) String beerName,
            @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
            @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){

        // Default inventory flag to false
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        // Use default page number if none given
        if (pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        // Use default page size if invalid
        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        // Get paged list of beers from service
        BeerPagedList beerList = beerService.listBeers(
                beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);

        // Return list with status 200 OK
        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }

    @GetMapping("beer/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(
            @PathVariable("beerId") UUID beerId,
            @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){

        // Default inventory display to false
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        // Get single beer by ID
        return new ResponseEntity<>(beerService.getById(beerId, showInventoryOnHand), HttpStatus.OK);
    }

    @GetMapping("beerUpc/{upc}")
    public ResponseEntity<BeerDto> getBeerByUpc(@PathVariable("upc") String upc){
        // Get beer by UPC code
        return new ResponseEntity<>(beerService.getByUpc(upc), HttpStatus.OK);
    }

    @PostMapping(path = "beer")
    public ResponseEntity saveNewBeer(@RequestBody @Validated BeerDto beerDto){

        // Save new beer
        BeerDto savedBeer = beerService.saveNewBeer(beerDto);

        // Return created status with location header
        return ResponseEntity
                .created(UriComponentsBuilder
                        .fromHttpUrl("http://api.springframework.guru/api/v1/beer/" + savedBeer.getId().toString())
                        .build().toUri())
                .build();
    }

    @PutMapping("beer/{beerId}")
    public ResponseEntity updateBeerById(
            @PathVariable("beerId") UUID beerId,
            @RequestBody @Validated BeerDto beerDto){

        // Update beer and return 204 No Content
        return new ResponseEntity<>(beerService.updateBeer(beerId, beerDto), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("beer/{beerId}")
    public ResponseEntity<Void> deleteBeerById(@PathVariable("beerId") UUID beerId){

        // Delete beer by ID
        beerService.deleteBeerById(beerId);

        // Return empty response
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}