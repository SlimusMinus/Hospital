package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.entity.Sick;
import org.example.service.SickService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sicks")
@AllArgsConstructor
public class SickController {

    private final SickService service;

    /**
     * Получение списка всех болезней.
     *
     * @return список всех болезней в виде {@link ResponseEntity} с объектами {@link Sick}.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Sick>> findAll() {
        return service.findAll();
    }

    /**
     * Получение болезни по идентификатору.
     *
     * @param id идентификатор болезни.
     * @return болезнь в виде {@link ResponseEntity} с объектом {@link Sick}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sick> findById(@PathVariable int id) {
        return service.findById(id);
    }

    /**
     * Сохранение новой болезни.
     *
     * @param sick данные новой болезни.
     * @return сохраненная болезнь в виде {@link ResponseEntity} с объектом {@link Sick}.
     */
    @PostMapping
    public ResponseEntity<Sick> save(@RequestBody Sick sick) {
        return service.save(sick);
    }

    /**
     * Обновление информации о болезни.
     *
     * @param sick данные болезни для обновления.
     * @return обновленная болезнь в виде {@link ResponseEntity} с объектом {@link Sick}.
     */
    @PutMapping
    public ResponseEntity<Sick> update(@RequestBody Sick sick) {
        return service.update(sick);
    }

    /**
     * Удаление болезни по идентификатору.
     *
     * @param id идентификатор болезни.
     * @return пустой ответ в виде {@link ResponseEntity<Void>}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteById(id);
    }
}
