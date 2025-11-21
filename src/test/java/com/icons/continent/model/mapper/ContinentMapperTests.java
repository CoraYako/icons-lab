package com.icons.continent.model.mapper;

import com.icons.continent.model.ContinentEntity;
import com.icons.continent.model.dto.ContinentRequestDTO;
import com.icons.continent.model.dto.ContinentResponseDTO;
import com.icons.country.model.CountryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ContinentMapper unit tests")
public class ContinentMapperTests {
    private ContinentMapperImpl continentMapper;

    @BeforeEach
    public void setUp() {
        continentMapper = new ContinentMapperImpl();
    }

    @Nested
    @DisplayName("Tests for toEntity() DTO -> Entity")
    class DTOToEntityTests {
        @Test
        public void shouldMapDTOToEntity_whenDTOIsValid() {
            ContinentRequestDTO dto = new ContinentRequestDTO("image.url.com", "Asia");

            ContinentEntity entity = continentMapper.toEntity(dto);

            assertThat(entity).isNotNull();
            assertThat(entity.getId()).isNull(); // non persisted entity, so must be null
            assertThat(entity.getName()).isEqualTo(dto.name());
            assertThat(entity.getImageURL()).isEqualTo(dto.imageURL());
            assertThat(entity.getCountries()).isEmpty(); // initialized but no data
        }

        @Test
        public void shouldThrowException_whenDTOIsNull() {
            assertThrows(NullPointerException.class, () ->
                    continentMapper.toEntity(null)
            );
        }
    }

    @Nested
    @DisplayName("Tests for toDTO() Entity -> DTO")
    class EntityToDTOTests {
        @Test
        public void shouldMapEntityToDTO_whenCountriesCollectionIsEmpty() {
            ContinentEntity entity = new ContinentEntity("UUID-1", "image.url.com", "Asia");

            ContinentResponseDTO response = continentMapper.toDTO(entity);

            assertThat(response).isNotNull();
            assertThat(response.id()).isEqualTo(entity.getId());
            assertThat(response.name()).isEqualTo(entity.getName());
            assertThat(response.imageURL()).isEqualTo(entity.getImageURL());
            assertThat(response.countries()).isEmpty(); // initialized but no data
        }

        @Test
        public void shouldThrowException_whenEntityIsNull() {
            assertThrows(NullPointerException.class, () ->
                    continentMapper.toDTO(null)
            );
        }

        @Test
        public void shouldMapEntityToDTO_whenCountriesCollectionExist() {
            Set<CountryEntity> countries = Set.of(
                    new CountryEntity("UUID-1", "image.com", "Japan", 123456789, 378000),
                    new CountryEntity("UUID-2", "image.com", "Vietnam", 123456789, 35000),
                    new CountryEntity("UUID-3", "image.com", "Singapur", 123456789, 30000)
            );

            ContinentEntity entity = new ContinentEntity("UUID-12", "image.url.com", "Asia");
            entity.getCountries().addAll(countries);

            ContinentResponseDTO response = continentMapper.toDTO(entity);

            assertThat(response).isNotNull();
            assertThat(response.id()).isEqualTo(entity.getId());
            assertThat(response.name()).isEqualTo(entity.getName());
            assertThat(response.imageURL()).isEqualTo(entity.getImageURL());

            Set<String> expectedCountryNames = Set.of("Japan", "Vietnam", "Singapur");

            assertThat(response.countries()).isNotEmpty();
            assertThat(response.countries()).containsExactlyInAnyOrderElementsOf(expectedCountryNames);
        }
    }
}
