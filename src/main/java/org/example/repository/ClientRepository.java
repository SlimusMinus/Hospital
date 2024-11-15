package org.example.repository;

import org.example.entity.Client;

import java.util.List;

public interface ClientRepository {
    List<Client> findAll();
    Client findById(int id);
    Client save(Client client);
    boolean delete(Client client);
}
