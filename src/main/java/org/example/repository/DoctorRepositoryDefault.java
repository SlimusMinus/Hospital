package org.example.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Doctor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class DoctorRepositoryDefault implements DoctorRepository {
    @Override
    public List<Doctor> findAll() {
        return List.of();
    }

    @Override
    public Doctor findById(int id) {
        return null;
    }

    @Override
    public Doctor saveOrUpdate(Doctor doctor) {
        return null;
    }

    @Override
    public boolean delete(Doctor doctor) {
        return false;
    }
}
