package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.entity.Doctor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.service.DoctorService;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@AllArgsConstructor
public class DoctorController {
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<Doctor>> findAll() {
        return ResponseEntity.ok(doctorService.findAll());
    }

    @GetMapping("{/id}")
    public ResponseEntity<Doctor> findById(@PathVariable int id) {
        return ResponseEntity.ok(doctorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Doctor> create(@RequestBody Doctor doctor) {
        return ResponseEntity.ok(doctorService.save(doctor));
    }

    @DeleteMapping
    public boolean delete(@RequestBody Doctor doctor) {
        return doctorService.delete(doctor);
    }
}
