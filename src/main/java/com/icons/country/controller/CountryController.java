package com.icons.country.controller;

import com.icons.country.model.dto.CountryFilterRequestDTO;
import com.icons.country.model.dto.CountryRequestDTO;
import com.icons.country.model.dto.CountryResponseDTO;
import com.icons.country.model.dto.CountryUpdateRequestDTO;
import com.icons.country.service.CountryService;
import com.icons.util.ApiUtils;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUtils.COUNTRY_BASE_URL)
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping()
    public ResponseEntity<@NonNull Void> createCountry(@Valid @RequestBody CountryRequestDTO dto) {
        countryService.createCountry(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(ApiUtils.URI_RESOURCE)
    public ResponseEntity<@NonNull CountryResponseDTO> updateCountry(@PathVariable String id,
                                                                     @RequestBody CountryUpdateRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(countryService.updateCountry(id, dto));
    }

    @GetMapping(ApiUtils.URI_RESOURCE)
    public ResponseEntity<@NonNull CountryResponseDTO> getCountryById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(countryService.getCountryById(id));
    }

    @DeleteMapping(ApiUtils.URI_RESOURCE)
    public ResponseEntity<@NonNull Void> deleteCountry(@PathVariable String id) {
        countryService.deleteCountry(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<@NonNull Page<@NonNull CountryResponseDTO>> listCountries(@RequestBody CountryFilterRequestDTO filters) {
        return ResponseEntity.status(HttpStatus.OK).body(countryService.listCountries(filters));
    }
}
