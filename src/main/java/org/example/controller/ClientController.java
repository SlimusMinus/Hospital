package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.service.ClientService;
import org.example.service.dto.ClientDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для управления клиентами.
 * Обрабатывает HTTP-запросы для операций с клиентами.
 * Использует {@link ClientService} для выполнения бизнес-логики.
 */
@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {

    private final ClientService service;

    /**
     * Получение списка всех клиентов.
     *
     * @return список всех клиентов в виде {@link ResponseEntity} с объектами {@link ClientDTO}.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClientDTO>> findAll() {
        return service.findAll();
    }

    /**
     * Получение клиента по идентификатору.
     *
     * @param id идентификатор клиента.
     * @return клиент в виде {@link ResponseEntity} с объектом {@link ClientDTO}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable int id) {
        return service.findById(id);
    }

    /**
     * Сохранение нового клиента.
     *
     * @param clientDTO данные нового клиента.
     * @return сохраненный клиент в виде {@link ResponseEntity} с объектом {@link ClientDTO}.
     */
    @PostMapping
    public ResponseEntity<ClientDTO> save(@RequestBody ClientDTO clientDTO) {
        return service.save(clientDTO);
    }

    /**
     * Обновление информации о клиенте.
     *
     * @param clientDTO данные клиента для обновления.
     * @return обновленный клиент в виде {@link ResponseEntity} с объектом {@link ClientDTO}.
     */
    @PutMapping
    public ResponseEntity<ClientDTO> update(@RequestBody ClientDTO clientDTO) {
        return service.update(clientDTO);
    }

    /**
     * Удаление клиента по идентификатору.
     *
     * @param id идентификатор клиента.
     * @return пустой ответ в виде {@link ResponseEntity<Void>}.
     */
    @DeleteMapping("{/id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteById(id);
    }
}
