package org.example.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Sick;
import org.example.repository.CRUDRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class SickService {

    private final CRUDRepository<Sick> repository;

    /**
     * Получить список всех болезней.
     *
     * @return ResponseEntity со списком объектов Sick.
     */
    public ResponseEntity<List<Sick>> findAll() {
        log.info("Вызов метода findAll для получения всех болезней");
        List<Sick> sicks = repository.findAll();
        log.info("Найдено {} болезней", sicks.size());
        return ResponseEntity.ok(sicks);
    }

    /**
     * Найти болезнь по идентификатору.
     *
     * @param id идентификатор болезни.
     * @return ResponseEntity с объектом Sick, если болезнь найдена, или статусом 404.
     */
    public ResponseEntity<Sick> findById(int id) {
        log.info("Вызов метода findById для поиска болезни с id: {}", id);
        Sick sick = repository.findById(id);
        if (sick == null) {
            log.warn("Болезнь с id {} не найдена", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Болезнь с id {} успешно найдена", id);
        return ResponseEntity.ok(sick);
    }

    /**
     * Сохранить новую болезнь.
     *
     * @param sick объект Sick для сохранения.
     * @return ResponseEntity с сохраненным объектом Sick.
     */
    public ResponseEntity<Sick> save(Sick sick) {
        log.info("Вызов метода save для сохранения болезни: {}", sick);
        Sick savedSick = repository.save(sick);
        log.info("Болезнь успешно сохранена с id: {}", savedSick.getId());
        return ResponseEntity.ok(savedSick);
    }

    /**
     * Обновить данные болезни.
     *
     * @param sick объект Sick с обновленными данными.
     * @return ResponseEntity с обновленным объектом Sick, или статусом 404, если болезнь не найдена.
     */
    public ResponseEntity<Sick> update(Sick sick) {
        log.info("Вызов метода update для обновления болезни: {}", sick);
        Sick updatedSick = repository.update(sick);
        if (updatedSick == null) {
            log.warn("Болезнь с id {} не найдена для обновления", sick.getId());
            return ResponseEntity.notFound().build();
        }
        log.info("Болезнь с id {} успешно обновлена", sick.getId());
        return ResponseEntity.ok(updatedSick);
    }

    /**
     * Удалить болезнь по идентификатору.
     *
     * @param id идентификатор болезни.
     * @return ResponseEntity со статусом 204 (успешно удалено) или 404 (не найдено).
     */
    public ResponseEntity<Void> deleteById(int id) {
        log.info("Вызов метода deleteById для удаления болезни с id: {}", id);
        boolean isDeleted = repository.delete(id);
        if (!isDeleted) {
            log.warn("Болезнь с id {} не найдена для удаления", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Болезнь с id {} успешно удалена", id);
        return ResponseEntity.noContent().build();
    }
}
