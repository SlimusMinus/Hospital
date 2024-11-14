package service;

import entity.Client;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import repository.ClientRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class ClientService {
    private ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(int id) {
        return clientRepository.findById(id);
    }

    public Client saveOrUpdate(Client client) {
        return clientRepository.save(client);
    }

    public boolean deleteById(Client client) {
        return clientRepository.delete(client);
    }
}
