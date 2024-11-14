package org.example.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Client;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class ClientRepositoryDefault implements ClientRepository {

    @Override
    public List<Client> findAll() {
        return List.of();
    }

    @Override
    public Client findById(int id) {
        return null;
    }

    @Override
    public Client save(Client client) {
        return null;
    }

    @Override
    public boolean delete(Client client) {
        return false;
    }
}
