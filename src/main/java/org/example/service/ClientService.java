package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Client;
import lombok.AllArgsConstructor;
import org.example.entity.Doctor;
import org.example.service.dto.ClientDTO;
import org.example.service.mapper.ClientMapper;
import org.example.service.mapper.DoctorMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.example.repository.CRUDRepository;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

@AllArgsConstructor
@Service
@Slf4j
public class ClientService {
    private CRUDRepository<Client> repository;

    public ResponseEntity<List<ClientDTO>> findAll() {
        List<ClientDTO> clients = repository.findAll().stream().map(ClientMapper.INSTANCE::clientToClientDTO).toList();
        if(clients.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clients);
    }

    public ResponseEntity<ClientDTO> findById(int id) {
        Optional<Client> client = Optional.ofNullable(repository.findById(id));
        if (client.isPresent()) {
            return ResponseEntity.ok(ClientMapper.INSTANCE.clientToClientDTO(client.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public  ResponseEntity<ClientDTO> save(ClientDTO clientDTO) {
        Client savedClient = repository.save(ClientMapper.INSTANCE.clientDTOToClient(clientDTO));
        return ResponseEntity.ok(ClientMapper.INSTANCE.clientToClientDTO(savedClient));
    }

    public ResponseEntity<ClientDTO> update(ClientDTO clientDTO) {
         /* if (!repository.existsById(parcelId)) {
            log.warn("Посылка с ID {} не найдена для обновления", parcelId);
            return ResponseEntity.notFound().build();
        }*/
        Client updatedClient = repository.update(ClientMapper.INSTANCE.clientDTOToClient(clientDTO));
        return ResponseEntity.ok(ClientMapper.INSTANCE.clientToClientDTO(updatedClient));
    }

    public ResponseEntity<Void> deleteById(int id) {
        boolean isDelete = repository.delete(id);
        if (!isDelete) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
