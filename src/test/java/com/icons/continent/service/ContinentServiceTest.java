package com.icons.continent.service;

import com.icons.continent.model.ContinentEntity;
import com.icons.continent.model.dto.ContinentRequestDTO;
import com.icons.continent.model.dto.ContinentResponseDTO;
import com.icons.continent.model.dto.ContinentUpdateRequestDTO;
import com.icons.continent.model.mapper.ContinentMapper;
import com.icons.continent.repository.ContinentRepository;
import com.icons.exception.*;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
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

    @Nested
    @DisplayName("Tests for createContinent()")
    class CreateContinentTests {
        private final String VALID_IMAGE_URL = "https://amazin-image.com/america";
        private final String VALID_NAME = "America";

        @Test
        public void shouldSaveAnEntity_whenCreateContinent() {
            final String VALID_ID = "Valid-UUID";

            ContinentEntity createdEntity = new ContinentEntity(VALID_ID, VALID_IMAGE_URL, VALID_NAME);
            ContinentResponseDTO expectedDTO = new ContinentResponseDTO(VALID_ID, VALID_IMAGE_URL, VALID_NAME, null);

            ContinentRequestDTO request = new ContinentRequestDTO(VALID_IMAGE_URL, VALID_NAME);

            when(continentRepository.save(any(ContinentEntity.class))).thenReturn(createdEntity);
            when(continentMapper.toEntity(request)).thenReturn(createdEntity);
            when(continentMapper.toDTO(createdEntity)).thenReturn(expectedDTO);

            ContinentResponseDTO actualResponse = continentService.createContinent(request);

            verify(continentRepository).save(createdEntity);
            verify(continentMapper).toEntity(request);
            verify(continentMapper).toDTO(createdEntity);

            assertThat(actualResponse).isNotNull();
            assertThat(actualResponse.id()).isEqualTo(expectedDTO.id());
            assertThat(actualResponse.imageURL()).isEqualTo(expectedDTO.imageURL());
            assertThat(actualResponse.name()).isEqualTo(expectedDTO.name());
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
        public void shouldThrowException_whenNameIsEmpty() {
            final String EMPTY_NAME = "";
            ContinentRequestDTO request = new ContinentRequestDTO("image_url", EMPTY_NAME);

            RuntimeException thrown = assertThrows(InvalidFieldException.class, () ->
                    continentService.createContinent(request));

            verify(continentRepository, never()).existsByName(any());
            verify(continentRepository, never()).save(any());

            verify(continentMapper, never()).toEntity(any());
            verify(continentMapper, never()).toDTO(any());

            assertThat(thrown.getMessage())
                    .containsIgnoringCase("must provide a continent name");
        }

        @Test
        public void shouldThrowException_whenNameIsBlank() {
            final String BLANK_NAME = "\t";
            ContinentRequestDTO request = new ContinentRequestDTO("image_url", BLANK_NAME);

            RuntimeException thrown = assertThrows(InvalidFieldException.class, () ->
                    continentService.createContinent(request));

            verify(continentRepository, never()).existsByName(any());
            verify(continentRepository, never()).save(any());

            verify(continentMapper, never()).toEntity(any());
            verify(continentMapper, never()).toDTO(any());

            assertThat(thrown.getMessage())
                    .containsIgnoringCase("must provide a continent name");
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

        private final String ORIGINAL_IMAGE_URL = "original image_url";
        private final String ORIGINAL_NAME = "original name";

        @Test
        public void shouldUpdateContinent_whenValidIdAndDtoProvided() {
            final String NEW_NAME = "New Continent Name";
            final String NEW_URL = "new.com/new_image.png";

            ContinentEntity existingEntity = new ContinentEntity(VALID_ID, ORIGINAL_IMAGE_URL, ORIGINAL_NAME);
            ContinentEntity entityUpdated = new ContinentEntity(VALID_ID, NEW_URL, NEW_NAME);

            ContinentUpdateRequestDTO updateRequest = new ContinentUpdateRequestDTO(NEW_URL, NEW_NAME);

            ContinentResponseDTO expectedDTO = new ContinentResponseDTO(VALID_ID, NEW_URL, NEW_NAME, null);

            when(continentRepository.findById(VALID_ID)).thenReturn(Optional.of(existingEntity));
            when(continentRepository.save(existingEntity)).thenReturn(entityUpdated);
            when(continentMapper.toDTO(entityUpdated)).thenReturn(expectedDTO);

            ContinentResponseDTO actualResponse = continentService.updateContinent(VALID_ID, updateRequest);

            verify(continentRepository).findById(VALID_ID);
            verify(continentRepository).save(any(ContinentEntity.class));
            verify(continentMapper).toDTO(entityUpdated);

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
        public void shouldUpdateImageURLOnly_whenNameIsNotChanged() {
            final String NEW_IMAGE_URL = "new.com/new_image.png";

            ContinentEntity existingEntity = new ContinentEntity(VALID_ID, ORIGINAL_IMAGE_URL, ORIGINAL_NAME);
            ContinentEntity entityUpdated = new ContinentEntity(VALID_ID, NEW_IMAGE_URL, ORIGINAL_NAME);

            ContinentUpdateRequestDTO updateRequest = new ContinentUpdateRequestDTO(NEW_IMAGE_URL, ORIGINAL_NAME);
            ContinentResponseDTO expectedDTO = new ContinentResponseDTO(VALID_ID, NEW_IMAGE_URL, ORIGINAL_NAME, null);

            when(continentRepository.findById(VALID_ID)).thenReturn(Optional.of(existingEntity));
            when(continentRepository.save(existingEntity)).thenReturn(entityUpdated);
            when(continentMapper.toDTO(entityUpdated)).thenReturn(expectedDTO);

            ContinentResponseDTO actualResponse = continentService.updateContinent(VALID_ID, updateRequest);

            verify(continentRepository).findById(VALID_ID);
            verify(continentRepository).save(existingEntity);
            verify(continentMapper).toDTO(entityUpdated);

            assertThat(actualResponse).isNotNull();
            assertThat(actualResponse.name()).isEqualTo(ORIGINAL_NAME);
            assertThat(actualResponse.imageURL()).isEqualTo(NEW_IMAGE_URL);
        }

        @Test
        public void shouldUpdateNameOnly_whenImageURLIsNotChanged() {
            final String NEW_NAME = "new continent name";

            ContinentEntity existingEntity = new ContinentEntity(VALID_ID, ORIGINAL_IMAGE_URL, ORIGINAL_NAME);
            ContinentEntity entityUpdated = new ContinentEntity(VALID_ID, ORIGINAL_IMAGE_URL, NEW_NAME);

            ContinentUpdateRequestDTO updateRequest = new ContinentUpdateRequestDTO(ORIGINAL_IMAGE_URL, NEW_NAME);
            ContinentResponseDTO expectedDTO = new ContinentResponseDTO(VALID_ID, ORIGINAL_IMAGE_URL, NEW_NAME, null);

            when(continentRepository.findById(VALID_ID)).thenReturn(Optional.of(existingEntity));
            when(continentRepository.save(existingEntity)).thenReturn(entityUpdated);
            when(continentMapper.toDTO(entityUpdated)).thenReturn(expectedDTO);

            ContinentResponseDTO actualResponse = continentService.updateContinent(VALID_ID, updateRequest);

            verify(continentRepository).findById(VALID_ID);
            verify(continentRepository).save(existingEntity);
            verify(continentMapper).toDTO(entityUpdated);

            assertThat(actualResponse).isNotNull();
            assertThat(actualResponse.name()).isEqualTo(NEW_NAME);
            assertThat(actualResponse.imageURL()).isEqualTo(ORIGINAL_IMAGE_URL);
        }

        @Test
        public void shouldNotApplyUpdates_whenUpdateDataIsBlank() {
            final String BLANK_IMAGE_URL = "\t";
            final String BLANK_NAME = "\t";

            ContinentEntity existingEntity = new ContinentEntity(VALID_ID, ORIGINAL_IMAGE_URL, ORIGINAL_NAME);

            ContinentUpdateRequestDTO updateRequest = new ContinentUpdateRequestDTO(BLANK_IMAGE_URL, BLANK_NAME);
            ContinentResponseDTO expectedDTO = new ContinentResponseDTO(VALID_ID, ORIGINAL_IMAGE_URL, ORIGINAL_NAME, null);

            when(continentRepository.findById(VALID_ID)).thenReturn(Optional.of(existingEntity));
            when(continentRepository.save(existingEntity)).thenReturn(existingEntity);
            when(continentMapper.toDTO(existingEntity)).thenReturn(expectedDTO);

            ContinentResponseDTO actualResponse = continentService.updateContinent(VALID_ID, updateRequest);

            verify(continentRepository).findById(VALID_ID);
            verify(continentRepository).save(existingEntity);
            verify(continentMapper).toDTO(existingEntity);

            assertThat(actualResponse).isNotNull();
            assertThat(actualResponse.name()).isNotBlank().isNotEmpty();
            assertThat(actualResponse.imageURL()).isNotBlank().isNotEmpty();

            assertThat(actualResponse.name()).isEqualTo(ORIGINAL_NAME);
            assertThat(actualResponse.imageURL()).isEqualTo(ORIGINAL_IMAGE_URL);
        }

        @Test
        public void shouldNotApplyUpdates_whenUpdateDataIsNull() {
            ContinentEntity existingEntity = new ContinentEntity(VALID_ID, ORIGINAL_IMAGE_URL, ORIGINAL_NAME);

            ContinentUpdateRequestDTO updateRequest = new ContinentUpdateRequestDTO(null, null);
            ContinentResponseDTO expectedDTO = new ContinentResponseDTO(VALID_ID, ORIGINAL_IMAGE_URL, ORIGINAL_NAME, null);

            when(continentRepository.findById(VALID_ID)).thenReturn(Optional.of(existingEntity));
            when(continentRepository.save(existingEntity)).thenReturn(existingEntity);
            when(continentMapper.toDTO(existingEntity)).thenReturn(expectedDTO);

            ContinentResponseDTO actualResponse = continentService.updateContinent(VALID_ID, updateRequest);

            verify(continentRepository).findById(VALID_ID);
            verify(continentRepository).save(existingEntity);
            verify(continentMapper).toDTO(existingEntity);

            assertThat(actualResponse).isNotNull();
            assertThat(actualResponse.name()).isNotNull();
            assertThat(actualResponse.imageURL()).isNotNull();

            assertThat(actualResponse.name()).isEqualTo(ORIGINAL_NAME);
            assertThat(actualResponse.imageURL()).isEqualTo(ORIGINAL_IMAGE_URL);
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
            verify(continentMapper, never()).toDTO(any(ContinentEntity.class));
        }
    }

    @Nested
    @DisplayName("Tests for listContinents()")
    class ListContinentsTests {
        private final int MOCK_PAGE_SIZE = 10;
        private final int PAGE_NUMBER = 0;

        @Test
        public void shouldReturnPaginatedDTOs_whenContentExist() {
            ContinentEntity entity1 = new ContinentEntity("UUID_1", "image_url", "America");
            ContinentEntity entity2 = new ContinentEntity("UUID_2", "image_url", "Europe");

            ContinentResponseDTO dto1 = new ContinentResponseDTO(entity1.getId(), entity1.getImageURL(), entity1.getName(), null);
            ContinentResponseDTO dto2 = new ContinentResponseDTO(entity2.getId(), entity2.getImageURL(), entity2.getName(), null);

            List<ContinentEntity> continents = List.of(entity1, entity2);

            Pageable pageable = PageRequest.of(PAGE_NUMBER, MOCK_PAGE_SIZE);

            Page<@NonNull ContinentEntity> mockEntityPage = new PageImpl<>(continents, pageable, continents.size());

            when(continentRepository.findAll(any(Pageable.class))).thenReturn(mockEntityPage);

            when(continentMapper.toDTO(entity1)).thenReturn(dto1);
            when(continentMapper.toDTO(entity2)).thenReturn(dto2);

            Page<@NonNull ContinentResponseDTO> resultPage = continentService.listContinents(PAGE_NUMBER);

            verify(continentRepository).findAll(any(Pageable.class));
            verify(continentMapper, times(2)).toDTO(any(ContinentEntity.class));

            assertThat(resultPage).isNotNull();
            assertThat(resultPage.getNumber()).isEqualTo(PAGE_NUMBER);
            assertThat(resultPage.getSize()).isEqualTo(MOCK_PAGE_SIZE);

            assertThat(resultPage.getContent()).hasSize(continents.size());
            assertThat(resultPage.getContent().get(0).name()).isEqualTo(entity1.getName());
            assertThat(resultPage.getContent().get(1).name()).isEqualTo(entity2.getName());
        }

        @Test
        public void shouldReturnEmptyPage_whenContentNotExist() {
            Pageable pageable = PageRequest.of(PAGE_NUMBER, MOCK_PAGE_SIZE);

            Page<@NonNull ContinentEntity> emptyPage = new PageImpl<>(emptyList(), pageable, 0);

            when(continentRepository.findAll(any(Pageable.class))).thenReturn(emptyPage);

            Page<@NonNull ContinentResponseDTO> resultPage = continentService.listContinents(PAGE_NUMBER);

            verify(continentRepository).findAll(any(Pageable.class));
            verify(continentMapper, never()).toDTO(any(ContinentEntity.class));

            assertThat(resultPage).isNotNull();
            assertThat(resultPage.getContent()).isEmpty();
            assertThat(resultPage.getTotalElements()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("Tests for getContinentById()")
    class GetContinentByIdTests {
        private final String VALID_ID = "a1b2c3d4-e5f6-7890-1234-567890abcdef";
        private final String INVALID_ID = "not_valid_id";

        @Test
        public void shouldReturnDTO_whenContinentFound() {
            ContinentEntity mockedEntity = new ContinentEntity(VALID_ID, "image_url", "America");
            ContinentResponseDTO expectedDTO = new ContinentResponseDTO(VALID_ID, mockedEntity.getImageURL(), mockedEntity.getName(), null);

            when(continentRepository.findById(VALID_ID)).thenReturn(Optional.of(mockedEntity));
            when(continentMapper.toDTO(mockedEntity)).thenReturn(expectedDTO);

            ContinentResponseDTO result = continentService.getContinentById(VALID_ID);

            assertThat(result).isNotNull();
            assertThat(result).isEqualTo(expectedDTO);

            verify(continentRepository).findById(VALID_ID);
            verify(continentMapper).toDTO(mockedEntity);
        }

        @Test
        public void shouldThrowException_whenContinentNotFound() {
            when(continentRepository.findById(VALID_ID)).thenReturn(Optional.empty());

            RuntimeException thrown = assertThrows(ResourceNotFoundException.class, () ->
                    continentService.getContinentById(VALID_ID)
            );

            verify(continentRepository).findById(VALID_ID);
            verify(continentMapper, never()).toDTO(any(ContinentEntity.class));

            assertThat(thrown.getMessage())
                    .containsIgnoringCase("Continent")
                    .contains(VALID_ID);
        }

        @Test
        public void shouldThrowException_whenIdIsInvalidUUID() {
            RuntimeException thrown = assertThrows(InvalidUUIDException.class, () ->
                    continentService.getContinentById(INVALID_ID)
            );

            verify(continentRepository, never()).findById(any(String.class));
            verify(continentMapper, never()).toDTO(any(ContinentEntity.class));

            assertThat(thrown.getMessage())
                    .contains(INVALID_ID);
        }
    }

    @Nested
    @DisplayName("Tests for getById()")
    class GetByIdTests {
        private final String VALID_ID = "a1b2c3d4-e5f6-7890-1234-567890abcdef";
        private final String INVALID_ID = "not_valid_id";

        @Test
        public void shouldReturnEntity_whenContinentFound() {
            ContinentEntity expectedEntity = new ContinentEntity(VALID_ID, "image_url", "America");

            when(continentRepository.findById(VALID_ID)).thenReturn(Optional.of(expectedEntity));

            ContinentEntity result = continentService.getById(VALID_ID);

            assertThat(result).isNotNull();
            assertThat(result).isEqualTo(expectedEntity);

            verify(continentRepository).findById(VALID_ID);
        }

        @Test
        public void shouldThrowException_whenContinentNotFound() {
            when(continentRepository.findById(VALID_ID)).thenReturn(Optional.empty());

            RuntimeException thrown = assertThrows(ResourceNotFoundException.class, () ->
                    continentService.getById(VALID_ID)
            );

            verify(continentRepository).findById(VALID_ID);
            verify(continentMapper, never()).toDTO(any(ContinentEntity.class));

            assertThat(thrown.getMessage())
                    .containsIgnoringCase("Continent")
                    .contains(VALID_ID);
        }

        @Test
        public void shouldThrowException_whenIdIsInvalidUUID() {
            RuntimeException thrown = assertThrows(InvalidUUIDException.class, () ->
                    continentService.getById(INVALID_ID)
            );

            verify(continentRepository, never()).findById(any(String.class));
            verify(continentMapper, never()).toDTO(any(ContinentEntity.class));

            assertThat(thrown.getMessage())
                    .contains(INVALID_ID);
        }
    }
}
