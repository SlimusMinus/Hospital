package org.example.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Client;
import org.example.entity.Sick;
import org.example.repository.CRUDRepository;
import org.example.service.dto.ClientDTO;
import org.example.service.mapper.ClientMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления клиентами.
 * <p>
 * Этот класс предоставляет методы для выполнения операций CRUD (создание, чтение, обновление, удаление)
 * с объектами Client и их преобразования в DTO для взаимодействия с внешними системами.
 *
 * <p>
 * Использует {@link CRUDRepository} для выполнения операций с базой данных,
 * а также {@link ClientMapper} для преобразования между сущностями и DTO.
 * </p>
 *
 * <p>
 * Логирование выполняется с использованием аннотации {@code @Slf4j}, что позволяет
 * отслеживать выполнение операций и обрабатывать возможные ошибки.
 * </p>
 */
@AllArgsConstructor
@Service
@Slf4j
public class ClientService {
    private CRUDRepository<Client> repository;

    /**
     * Получить список всех клиентов.
     *
     * @return ResponseEntity со списком объектов ClientDTO.
     */
    public ResponseEntity<List<ClientDTO>> findAll() {
        log.info("Вызов метода findAll для получения всех клиентов");
        List<ClientDTO> clientDTOS = new ArrayList<>();
        int index = 0;
        for (Client client : repository.findAll()) {
            List<Sick> sicks = client.getSicks();
            clientDTOS.add(ClientMapper.INSTANCE.clientToClientDTO(client));
            clientDTOS.get(index).setSicks(sicks);
            index++;
        }
        log.info("Найдено {} клиентов", clientDTOS.size());
        return ResponseEntity.ok(clientDTOS);
    }

    /**
     * Найти клиента по идентификатору.
     *
     * @param id идентификатор клиента.
     * @return ResponseEntity с объектом ClientDTO, если клиент найден, или статусом 404.
     */
    public ResponseEntity<ClientDTO> findById(int id) {
        log.info("Вызов метода findById для поиска клиента с id: {}", id);
        Optional<Client> client = Optional.ofNullable(repository.findById(id));
        if (client.isEmpty()) {
            log.warn("Клиент с id {} не найден", id);
            return ResponseEntity.notFound().build();
        }
        ClientDTO clientDTO = ClientMapper.INSTANCE.clientToClientDTO(client.get());
        clientDTO.setSicks(client.get().getSicks());
        log.info("Клиент с id {} успешно найден", id);
        return ResponseEntity.ok(clientDTO);
    }

    /**
     * Сохранить нового клиента.
     *
     * @param clientDTO объект ClientDTO, который нужно сохранить.
     * @return ResponseEntity с сохраненным объектом ClientDTO.
     */
    public ResponseEntity<ClientDTO> save(ClientDTO clientDTO) {
        log.info("Вызов метода save для сохранения клиента: {}", clientDTO);
        Client savedClient = repository.save(ClientMapper.INSTANCE.clientDTOToClient(clientDTO));
        log.info("Клиент успешно сохранен с id: {}", savedClient.getId());
        return ResponseEntity.ok(ClientMapper.INSTANCE.clientToClientDTO(savedClient));
    }

    /**
     * Обновить данные клиента.
     *
     * @param clientDTO объект ClientDTO с обновленными данными.
     * @return ResponseEntity с обновленным объектом ClientDTO, или статусом 404, если клиент не найден.
     */
    public ResponseEntity<ClientDTO> update(ClientDTO clientDTO) {
        log.info("Вызов метода update для обновления клиента: {}", clientDTO);
        Client updatedClient = repository.update(ClientMapper.INSTANCE.clientDTOToClient(clientDTO));
        if (updatedClient == null) {
            log.warn("Клиент с id {} не найден для обновления", clientDTO.getClientId());
            return ResponseEntity.notFound().build();
        }
        log.info("Клиент с id {} успешно обновлен", updatedClient.getId());
        return ResponseEntity.ok(ClientMapper.INSTANCE.clientToClientDTO(updatedClient));
    }

    /**
     * Удалить клиента по идентификатору.
     *
     * @param id идентификатор клиента.
     * @return ResponseEntity со статусом 204 (успешно удалено) или 404 (не найдено).
     */
    public ResponseEntity<Void> deleteById(int id) {
        log.info("Вызов метода deleteById для удаления клиента с id: {}", id);
        boolean isDelete = repository.delete(id);
        if (!isDelete) {
            log.warn("Клиент с id {} не найден для удаления", id);
            return ResponseEntity.noContent().build();
        } else {
            log.info("Клиент с id {} успешно удален", id);
            return ResponseEntity.notFound().build();
        }
    }
}
