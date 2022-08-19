package com.icons.controller;

import com.icons.dto.ContinentDTO;
import com.icons.service.ContinentService;
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

    @PostMapping("/save")
    public ResponseEntity<ContinentDTO> save(@RequestBody ContinentDTO dto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(continentService.save(dto));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<ContinentDTO>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(continentService.getAll());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleElementFoundException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
