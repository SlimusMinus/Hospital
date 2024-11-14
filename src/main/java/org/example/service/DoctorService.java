package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Doctor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.example.repository.DoctorRepository;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class DoctorService {
    private DoctorRepository doctorRepository;

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public Doctor findById(int id) {
        return doctorRepository.findById(id);
    }

    public Doctor save(Doctor doctor) {
        return doctorRepository.saveOrUpdate(doctor);
    }

    public boolean delete(Doctor doctor) {
       return doctorRepository.delete(doctor);
    }
}
