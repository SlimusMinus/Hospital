package org.example.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Client;
import org.example.entity.Sick;
import org.example.repository.CRUDRepository;
import org.example.service.dto.SickDTO;
import org.example.service.mapper.SickMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class SickService {

    private CRUDRepository<Sick> repository;

    public ResponseEntity<List<Sick>> findAll() {
        List<Sick> sicks = repository.findAll();
        // Клиенты уже будут установлены благодаря JPQL LEFT JOIN FETCH
        return ResponseEntity.ok(sicks);
    }
}
