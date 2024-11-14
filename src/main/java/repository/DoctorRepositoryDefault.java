package repository;

import entity.Doctor;

import java.util.List;

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
