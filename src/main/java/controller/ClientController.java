package controller;

import entity.Client;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import repository.ClientRepository;

import java.util.List;

@RestController("/clients")
@AllArgsConstructor
public class ClientController {

    private final ClientRepository service;

    @GetMapping
    public List<Client> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Client findById(@PathVariable int id) {
        return service.findById(id);
    }

    @PostMapping
    public Client save(@RequestBody Client client) {
        return service.save(client);
    }

    @DeleteMapping("{/id}")
    public boolean delete(@RequestBody Client client) {
        return service.delete(client);
    }
}
