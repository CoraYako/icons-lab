package com.icons.controller;

import com.icons.dto.IconDTO;
import com.icons.service.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("icon")
public class IconController {

    @Autowired
    private IconService iconService;

    @PostMapping("/save")
    public ResponseEntity<IconDTO> save(@RequestBody IconDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(iconService.save(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<IconDTO> update(@PathVariable String id, @RequestBody IconDTO dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iconService.update(id, dto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<IconDTO>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iconService.getAll());
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        iconService.delete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
