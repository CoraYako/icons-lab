package com.icons.icon.controller;

import com.icons.icon.model.dto.IconRequestDTO;
import com.icons.icon.model.dto.IconResponseDTO;
import com.icons.icon.model.dto.IconUpdateRequestDTO;
import com.icons.icon.service.IconService;
import com.icons.util.ApiUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(ApiUtils.ICON_BASE_URL)
public class IconController {
    private final IconService iconService;

    public IconController(IconService iconService) {
        this.iconService = iconService;
    }

    @PostMapping()
    public ResponseEntity<Void> createIcon(@Valid @RequestBody IconRequestDTO dto) {
        iconService.createIcon(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(ApiUtils.URI_RESOURCE)
    public ResponseEntity<IconResponseDTO> updateIcon(@PathVariable String id,
                                                      @RequestBody IconUpdateRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(iconService.updateIcon(id, dto));
    }

    @GetMapping(ApiUtils.URI_RESOURCE)
    public ResponseEntity<IconResponseDTO> getIconById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(iconService.getIconDTOById(id));
    }

    @GetMapping
    public ResponseEntity<Page<IconResponseDTO>> listIcons(
            @RequestParam(name = "page") int pageNumber,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) Set<String> countries,
            @RequestParam(required = false, defaultValue = "ASC") String order) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(iconService.listIcons(pageNumber, name, date, order, countries));
    }

    @DeleteMapping(ApiUtils.URI_RESOURCE)
    public ResponseEntity<Void> deleteIcon(@PathVariable String id) {
        iconService.deleteIcon(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
