package com.icons.continent.service;

import com.icons.continent.model.ContinentEntity;
import com.icons.continent.model.dto.ContinentRequestDTO;
import com.icons.continent.model.dto.ContinentResponseDTO;
import com.icons.continent.model.dto.ContinentUpdateRequestDTO;
import com.icons.continent.model.mapper.ContinentMapper;
import com.icons.continent.repository.ContinentRepository;
import com.icons.exception.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ContinentService Unit Tests")
public class ContinentServiceTest {
    @Mock
    private ContinentRepository continentRepository;
    @Mock
    private ContinentMapper continentMapper;

    @InjectMocks
    private ContinentServiceImpl continentService;

    private ContinentEntity mockApplyUpdates(ContinentEntity existingEntity, ContinentUpdateRequestDTO dto) {
        ContinentEntity updated = new ContinentEntity();
        updated.setName(dto.name() != null ? dto.name() : existingEntity.getName());
        updated.setImageURL(dto.imageURL() != null ? dto.imageURL() : existingEntity.getImageURL());
        return updated;
    }

    private ContinentEntity buildMockEntity(String uuid, ContinentRequestDTO request) {
        return new ContinentEntity(uuid, request.imageURL(), request.name());
    }

    @Nested
    @DisplayName("Tests for createContinent()")
    class CreateContinentTests {
        private final String VALID_IMAGE_URL = "https://amazin-image.com/america";
        private final String VALID_NAME = "America";

        @Test
        public void shouldSaveAnEntity_whenCreateContinent() {
            ContinentRequestDTO request = new ContinentRequestDTO(VALID_IMAGE_URL, VALID_NAME);
            ContinentEntity mockedEntity = buildMockEntity("UUID-1", request);
            ContinentResponseDTO response = new ContinentResponseDTO(
                    mockedEntity.getId(),
                    mockedEntity.getImageURL(),
                    mockedEntity.getName(),
                    null
            );

            when(continentRepository.save(any(ContinentEntity.class))).thenReturn(mockedEntity);

            when(continentMapper.toEntity(any(ContinentRequestDTO.class))).thenReturn(mockedEntity);
            when(continentMapper.toDTO(any(ContinentEntity.class))).thenReturn(response);

            var result = continentService.createContinent(request);

            verify(continentRepository).save(any(ContinentEntity.class));
            verify(continentMapper).toEntity(any(ContinentRequestDTO.class));
            verify(continentMapper).toDTO(any(ContinentEntity.class));

            assertThat(result).isNotNull();
            assertEquals(result, response);
        }

        @Test
        public void shouldThrowException_whenDtoIsNull() {
            RuntimeException exception = assertThrows(NullRequestBodyException.class, () ->
                    continentService.createContinent(null));

            assertThat(exception.getMessage())
                    .containsIgnoringCase("Continent");

            verify(continentRepository, never()).existsByName(any());
            verify(continentRepository, never()).save(any());
            verify(continentMapper, never()).toEntity(any());
            verify(continentMapper, never()).toDTO(any());
        }

        @Test
        public void shouldThrowException_whenNameIsNull() {
            ContinentRequestDTO request = new ContinentRequestDTO(null, null);

            RuntimeException exception = assertThrows(InvalidFieldException.class, () ->
                    continentService.createContinent(request));

            assertThat(exception.getMessage())
                    .containsIgnoringCase("Continent")
                    .containsIgnoringCase("name");

            verify(continentRepository, never()).save(any());
            verify(continentMapper, never()).toEntity(any());
            verify(continentMapper, never()).toDTO(any());
        }

        @Test
        public void shouldThrowException_whenContinentAlreadyExists() {
            ContinentRequestDTO request = new ContinentRequestDTO(VALID_IMAGE_URL, VALID_NAME);

            when(continentRepository.existsByName(request.name())).thenReturn(true);

            RuntimeException exception = assertThrows(DuplicatedResourceException.class, () ->
                    continentService.createContinent(request)
            );

            assertThat(exception.getMessage())
                    .containsIgnoringCase("Continent")
                    .containsIgnoringCase(request.name());

            verify(continentRepository).existsByName(request.name());
            verify(continentRepository, never()).save(any());
            verify(continentMapper, never()).toEntity(any());
            verify(continentMapper, never()).toDTO(any());
        }
    }

    @Nested
    @DisplayName("Tests for updateContinent()")
    class UpdateContinentTests {
        private final String VALID_ID = "a1b2c3d4-e5f6-7890-1234-567890abcdef";
        private final String INVALID_ID = "not-a-uuid";
        private final String ORIGINAL_NAME = "Old Continent";
        private final String ORIGINAL_URL = "old.com/image.jpg";

        @Test
        public void shouldUpdateContinent_whenValidIdAndDtoProvided() {
            final String NEW_NAME = "New Continent Name";
            final String NEW_URL = "new.com/new_image.png";

            ContinentRequestDTO originalRequest = new ContinentRequestDTO(ORIGINAL_URL, ORIGINAL_NAME);
            ContinentEntity existingEntity = buildMockEntity(VALID_ID, originalRequest);

            ContinentUpdateRequestDTO updateRequest = new ContinentUpdateRequestDTO(NEW_URL, NEW_NAME);

            ContinentEntity savedEntity = mockApplyUpdates(existingEntity, updateRequest);

            ContinentResponseDTO expectedDTO = new ContinentResponseDTO(VALID_ID, NEW_URL, NEW_NAME, null);

            when(continentRepository.findById(VALID_ID)).thenReturn(Optional.of(existingEntity));
            when(continentRepository.existsByName(NEW_NAME)).thenReturn(false);
            when(continentRepository.save(any(ContinentEntity.class))).thenReturn(savedEntity);
            when(continentMapper.toDTO(savedEntity)).thenReturn(expectedDTO);

            ContinentResponseDTO actualResponse = continentService.updateContinent(VALID_ID, updateRequest);

            verify(continentRepository).findById(VALID_ID);
            verify(continentRepository).save(any(ContinentEntity.class));
            verify(continentMapper).toDTO(savedEntity);

            assertThat(actualResponse).isNotNull();
            assertThat(actualResponse.name()).isEqualTo(NEW_NAME);
            assertThat(actualResponse.imageURL()).isEqualTo(NEW_URL);
        }

        @Test
        public void shouldThrowException_whenDtoIsNull() {
            RuntimeException thrown = assertThrows(NullRequestBodyException.class, () ->
                    continentService.updateContinent(VALID_ID, null)
            );

            assertThat(thrown.getMessage())
                    .containsIgnoringCase("Continent");

            verify(continentRepository, never()).findById(any());
            verify(continentRepository, never()).save(any());
        }

        @Test
        public void shouldThrowException_whenIdIsInvalidUUID() {
            ContinentUpdateRequestDTO updateRequest = new ContinentUpdateRequestDTO("url", "name");

            RuntimeException thrown = assertThrows(InvalidUUIDException.class, () ->
                    continentService.updateContinent(INVALID_ID, updateRequest)
            );

            assertThat(thrown.getMessage())
                    .containsIgnoringCase(INVALID_ID);

            verify(continentRepository, never()).findById(any());
            verify(continentRepository, never()).save(any(ContinentEntity.class));
        }

        @Test
        public void shouldThrowException_whenContinentNotFound() {
            ContinentUpdateRequestDTO updateRequest = new ContinentUpdateRequestDTO("url", "name");

            when(continentRepository.findById(VALID_ID)).thenReturn(Optional.empty());

            RuntimeException thrown = assertThrows(ResourceNotFoundException.class, () ->
                    continentService.updateContinent(VALID_ID, updateRequest)
            );

            assertThat(thrown.getMessage())
                    .containsIgnoringCase("Continent")
                    .containsIgnoringCase(VALID_ID);

            verify(continentRepository).findById(VALID_ID);
            verify(continentRepository, never()).save(any(ContinentEntity.class));
        }

        @Test
        public void shouldThrowException_whenNewNameIsDuplicated() {
            final String DUPLICATE_NAME = "Europe";
            ContinentUpdateRequestDTO updateRequest = new ContinentUpdateRequestDTO("url", DUPLICATE_NAME);

            when(continentRepository.existsByName(DUPLICATE_NAME)).thenReturn(true);

            RuntimeException thrown = assertThrows(DuplicatedResourceException.class, () ->
                    continentService.updateContinent(VALID_ID, updateRequest)
            );

            assertThat(thrown.getMessage())
                    .containsIgnoringCase("Continent")
                    .contains(DUPLICATE_NAME);

            verify(continentRepository).existsByName(DUPLICATE_NAME);
            verify(continentRepository, never()).findById(any());
            verify(continentRepository, never()).save(any());
        }
    }
}
