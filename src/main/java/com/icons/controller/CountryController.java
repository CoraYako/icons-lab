package com.icons.controller;

import com.icons.dto.CountryDTO;
import com.icons.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("country")
public class CountryController {

    private CountryService countryService;

    @Autowired
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    public CountryService getCountryService() {
        return countryService;
    }

    @PostMapping("/save")
    public ResponseEntity<CountryDTO> save(@RequestBody CountryDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(getCountryService().save(dto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<CountryDTO>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getCountryService().getAll());
    }
}
