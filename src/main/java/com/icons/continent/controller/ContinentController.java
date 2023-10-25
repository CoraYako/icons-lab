package com.icons.continent.controller;

import com.icons.continent.model.dto.ContinentRequestDTO;
import com.icons.continent.model.dto.ContinentResponseDTO;
import com.icons.continent.model.dto.ContinentUpdateRequestDTO;
import com.icons.continent.service.ContinentService;
import com.icons.util.ApiUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUtils.CONTINENT_BASE_URL)
public class ContinentController {

    private final ContinentService continentService;

    public ContinentController(ContinentService continentService) {
        this.continentService = continentService;
    }

    @PostMapping()
    public ResponseEntity<Void> createContinent(@Valid @RequestBody ContinentRequestDTO dto) {
        continentService.createContinent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(ApiUtils.URI_RESOURCE)
    public ResponseEntity<ContinentResponseDTO> updateContinent(@PathVariable String id,
                                                                @RequestBody ContinentUpdateRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(continentService.updateContinent(id, dto));
    }

    @GetMapping(ApiUtils.URI_RESOURCE)
    public ResponseEntity<ContinentResponseDTO> getContinentById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(continentService.getContinentDTOById(id));
    }

    @GetMapping()
    public ResponseEntity<Page<ContinentResponseDTO>> listContinents(@RequestParam(name = "page") int pageNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(continentService.listContinents(pageNumber));
    }
}
