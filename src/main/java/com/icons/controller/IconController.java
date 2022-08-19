package com.icons.controller;

import com.icons.dto.IconDTO;
import com.icons.service.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("icon")
public class IconController {

    @Autowired
    private IconService iconService;

    @PostMapping("/save")
    public ResponseEntity<IconDTO> save(IconDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(iconService.save(dto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<IconDTO>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iconService.getAll());
    }
}
