package org.example.repository;

import org.example.entity.Client;
import org.example.entity.Doctor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientRepository implements CRUDRepository<Client> {

    @Override
    public List<Client> findAll() {
        return List.of();
    }

    @Override
    public Client findById(int id) {
        return null;
    }

    @Override
    public Client save(Client entity) {
        return null;
    }

    @Override
    public Client update(Client entity) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
