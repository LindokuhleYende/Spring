package guru.springframework.sfgrestbrewery.services;

import guru.springframework.sfgrestbrewery.domain.Beer;
import guru.springframework.sfgrestbrewery.repositories.BeerRepository;
import guru.springframework.sfgrestbrewery.web.controller.NotFoundException;
import guru.springframework.sfgrestbrewery.web.mappers.BeerMapper;
import guru.springframework.sfgrestbrewery.web.model.BeerDto;
import guru.springframework.sfgrestbrewery.web.model.BeerPagedList;
import guru.springframework.sfgrestbrewery.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by jt on 2019-04-20.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    // Inject repository and mapper
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    /**
     * List beers with optional search by name/style.
     * Uses caching when inventory is NOT requested.
     */
    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {

        Page<Beer> beerPage;

        // Decide which search query to run
        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName)) {
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (!StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        // Map beers differently depending on inventory flag
        if (showInventoryOnHand) {
            return new BeerPagedList(
                    beerPage.getContent()
                            .stream()
                            .map(beerMapper::beerToBeerDtoWithInventory)
                            .collect(Collectors.toList()),
                    beerPage.getPageable(),
                    beerPage.getTotalElements()
            );
        }

        return new BeerPagedList(
                beerPage.getContent()
                        .stream()
                        .map(beerMapper::beerToBeerDto)
                        .collect(Collectors.toList()),
                beerPage.getPageable(),
                beerPage.getTotalElements()
        );
    }

    /**
     * Get beer by ID (cached when inventory is not shown)
     */
    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        return showInventoryOnHand ?
                beerMapper.beerToBeerDtoWithInventory(beer) :
                beerMapper.beerToBeerDto(beer);
    }

    /**
     * Save a new beer.
     */
    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(
                beerRepository.save(beerMapper.beerDtoToBeer(beerDto))
        );
    }

    /**
     * Update existing beer fields.
     */
    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(BeerStyleEnum.PILSNER.valueOf(beerDto.getBeerStyle()));
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }

    /**
     * Get beer using UPC (cached)
     */
    @Cacheable(cacheNames = "beerUpcCache")
    @Override
    public BeerDto getByUpc(String upc) {
        return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc));
    }

    /**
     * Delete beer by ID.
     */
    @Override
    public void deleteBeerById(UUID beerId) {
        beerRepository.deleteById(beerId);
    }
}