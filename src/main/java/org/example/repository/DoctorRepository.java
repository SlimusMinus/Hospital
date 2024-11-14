package org.example.repository;

import org.example.entity.Doctor;

import java.util.List;

public interface DoctorRepository {
    List<Doctor> findAll();
    Doctor findById(int id);
    Doctor saveOrUpdate(Doctor doctor);
    boolean delete(Doctor doctor);
}
