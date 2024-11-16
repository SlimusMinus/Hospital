package org.example.repository;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Client;
import org.example.entity.Sick;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class SickRepository implements CRUDRepository<Sick> {
    private final EntityManager entityManager;


    @Override
    public List<Sick> findAll() {
        log.info("Вызов метода findAll для получения всех клиентов");
        String jpql = "SELECT s FROM Sick s LEFT JOIN FETCH s.clients";
        List<Sick> sicks = entityManager.createQuery(jpql, Sick.class).getResultList();
        log.info("Найдено {} клиентов", sicks.size());
        return sicks;
    }

    @Override
    public Sick findById(int id) {
        return null;
    }

    @Override
    public Sick save(Sick entity) {
        return null;
    }

    @Override
    public Sick update(Sick entity) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
