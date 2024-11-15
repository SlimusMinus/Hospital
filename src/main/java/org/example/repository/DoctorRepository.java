package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.entity.Client;
import org.example.entity.Doctor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DoctorRepository implements CRUDRepository<Doctor> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Doctor> findAll() {
        return entityManager.createQuery("SELECT d FROM Doctor d", Doctor.class).getResultList();
    }

    @Override
    public Doctor findById(int id) {
        return entityManager.find(Doctor.class, id);
    }

    @Override
    public Doctor save(Doctor doctor) {
        entityManager.persist(doctor);
        return doctor;
    }

    @Override
    public Doctor update(Doctor doctor) {
        return entityManager.merge(doctor);
    }

    @Override
    public boolean delete(int id) {

        return true;
    }
}
