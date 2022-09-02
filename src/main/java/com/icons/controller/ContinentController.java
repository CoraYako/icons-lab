package com.icons.controller;

import com.icons.dto.ContinentDTO;
import com.icons.service.implement.ContinentServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("continent")
public class ContinentController {

    @Autowired
    private ContinentServiceImplement continentServiceImplement;

    @PostMapping("/save")
    public ResponseEntity<ContinentDTO> save(@Valid @RequestBody ContinentDTO dto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(continentServiceImplement.save(dto));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<ContinentDTO>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(continentServiceImplement.getAll());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleElementFoundException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
