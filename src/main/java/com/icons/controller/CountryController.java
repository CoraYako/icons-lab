package com.icons.controller;

import com.icons.dto.CountryDTO;
import com.icons.service.implement.CountryServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("country")
public class CountryController {

    @Autowired
    private CountryServiceImplement countryServiceImplement;

    @PostMapping("/save")
    public ResponseEntity<CountryDTO> save(@RequestBody CountryDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(countryServiceImplement.save(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CountryDTO> update(@PathVariable String id, @RequestBody CountryDTO dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(countryServiceImplement.update(id, dto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<CountryDTO>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(countryServiceImplement.getAll());
    }

    @GetMapping
    public ResponseEntity<List<CountryDTO>> getByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String continent,
            @RequestParam(required = false) List<String> icons,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(countryServiceImplement.getByFilters(name, continent, icons, order));
    }
}
