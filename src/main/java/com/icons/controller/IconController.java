package com.icons.controller;

import com.icons.dto.IconDTO;
import com.icons.service.implement.IconServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/icon")
public class IconController {

    @Autowired
    private IconServiceImplement iconServiceImplement;

    @PostMapping("/save")
    public ResponseEntity<IconDTO> save(@Valid @RequestBody IconDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(iconServiceImplement.save(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<IconDTO> update(@PathVariable String id, @RequestBody IconDTO dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iconServiceImplement.update(id, dto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<IconDTO>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iconServiceImplement.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<IconDTO>> getByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) List<String> countries,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        List<IconDTO> dtoList = iconServiceImplement.getByFilters(name, date, countries, order);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dtoList);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        iconServiceImplement.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
