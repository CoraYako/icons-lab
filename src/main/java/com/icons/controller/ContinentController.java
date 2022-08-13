package com.icons.controller;

import com.icons.dto.ContinentDTO;
import com.icons.service.implement.ContinentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("continent")
public class ContinentController {

    @Autowired
    private ContinentService continentService;

    @GetMapping("/list")
    public ResponseEntity<List<ContinentDTO>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(continentService.getAll());
    }

    @PostMapping("/save")
    public ResponseEntity<ContinentDTO> save(@RequestBody ContinentDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(continentService.save(dto));
    }
}
