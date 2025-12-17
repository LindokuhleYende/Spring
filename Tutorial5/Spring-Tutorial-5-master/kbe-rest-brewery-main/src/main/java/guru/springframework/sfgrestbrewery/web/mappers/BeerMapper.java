package guru.springframework.sfgrestbrewery.web.mappers;

import guru.springframework.sfgrestbrewery.domain.Beer;
import guru.springframework.sfgrestbrewery.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DateMapper.class})
public interface BeerMapper {

    // Convert entity → DTO (without inventory)
    BeerDto beerToBeerDto(Beer beer);

    // Convert entity → DTO (includes inventory field)
    BeerDto beerToBeerDtoWithInventory(Beer beer);

    // Convert DTO → entity
    Beer beerDtoToBeer(BeerDto dto);
}