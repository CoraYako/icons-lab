package com.icons.continent.controller;

import com.icons.continent.model.dto.ContinentRequestDTO;
import com.icons.continent.model.dto.ContinentResponseDTO;
import com.icons.continent.model.dto.ContinentUpdateRequestDTO;
import com.icons.continent.service.ContinentService;
import com.icons.util.ApiUtils;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(ApiUtils.CONTINENT_BASE_URL)
public class ContinentController {

    private final ContinentService continentService;

    public ContinentController(ContinentService continentService) {
        this.continentService = continentService;
    }

    @PostMapping()
    public ResponseEntity<@NonNull ContinentResponseDTO> createContinent(@Valid @RequestBody ContinentRequestDTO dto) {
        var continent = continentService.createContinent(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(ApiUtils.URI_RESOURCE)
                .buildAndExpand(continent.id())
                .toUri();
        return ResponseEntity.created(location).body(continent);
    }

    @PatchMapping(ApiUtils.URI_RESOURCE)
    public ResponseEntity<@NonNull ContinentResponseDTO> updateContinent(@PathVariable String id,
                                                                         @RequestBody ContinentUpdateRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(continentService.updateContinent(id, dto));
    }

    @GetMapping(ApiUtils.URI_RESOURCE)
    public ResponseEntity<@NonNull ContinentResponseDTO> getContinentById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(continentService.getContinentById(id));
    }

    @GetMapping()
    public ResponseEntity<@NonNull Page<@NonNull ContinentResponseDTO>> listContinents(@RequestParam(name = "page") int pageNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(continentService.listContinents(pageNumber));
    }
}
