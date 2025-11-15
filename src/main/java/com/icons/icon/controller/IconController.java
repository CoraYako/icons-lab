package com.icons.icon.controller;

import com.icons.icon.model.dto.IconFilterRequestDTO;
import com.icons.icon.model.dto.IconRequestDTO;
import com.icons.icon.model.dto.IconResponseDTO;
import com.icons.icon.model.dto.IconUpdateRequestDTO;
import com.icons.icon.service.IconService;
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
@RequestMapping(ApiUtils.ICON_BASE_URL)
public class IconController {
    private final IconService iconService;

    public IconController(IconService iconService) {
        this.iconService = iconService;
    }

    @PostMapping()
    public ResponseEntity<@NonNull IconResponseDTO> createIcon(@Valid @RequestBody IconRequestDTO dto) {
        var icon = iconService.createIcon(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(ApiUtils.URI_RESOURCE)
                .buildAndExpand(icon.id())
                .toUri();
        return ResponseEntity.created(location).body(icon);
    }

    @PatchMapping(ApiUtils.URI_RESOURCE)
    public ResponseEntity<@NonNull IconResponseDTO> updateIcon(@PathVariable String id, @RequestBody IconUpdateRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(iconService.updateIcon(id, dto));
    }

    @GetMapping(ApiUtils.URI_RESOURCE)
    public ResponseEntity<@NonNull IconResponseDTO> getIconById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(iconService.getIconById(id));
    }

    @GetMapping
    public ResponseEntity<@NonNull Page<@NonNull IconResponseDTO>> listIcons(@RequestBody IconFilterRequestDTO filters) {
        return ResponseEntity.status(HttpStatus.OK).body(iconService.listIcons(filters));
    }

    @DeleteMapping(ApiUtils.URI_RESOURCE)
    public ResponseEntity<@NonNull Void> deleteIcon(@PathVariable String id) {
        iconService.deleteIcon(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
