package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.entity.Sick;
import org.example.service.SickService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sicks")
@AllArgsConstructor
public class SickController {

    private final SickService service;

    @GetMapping
    public ResponseEntity<List<Sick>> findAll() {
       return service.findAll();
    }
}
