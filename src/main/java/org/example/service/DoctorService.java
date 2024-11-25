package org.example.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Client;
import org.example.entity.Doctor;
import org.example.repository.CRUDRepository;
import org.example.service.dto.DoctorDTO;
import org.example.service.mapper.DoctorMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления данными о врачах.
 *
 * Этот класс предоставляет методы для выполнения операций CRUD (создание, чтение, обновление, удаление)
 * с объектами Doctor и их преобразования в DTO для взаимодействия с внешними системами.
 *
 * <p>
 * Использует {@link CRUDRepository} для выполнения операций с базой данных,
 * а также {@link DoctorMapper} для преобразования между сущностями и DTO.
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
public class DoctorService {
    private CRUDRepository<Doctor> repository;

    /**
     * Получить список всех врачей.
     *
     * @return ResponseEntity со списком объектов DoctorDTO.
     */
    public ResponseEntity<List<DoctorDTO>> findAll() {
        log.info("Вызов метода findAll для получения всех врачей");
        List<DoctorDTO> doctorDTOS = new ArrayList<>();
        int index = 0;
        for (Doctor doctor : repository.findAll()) {
            List<Client> clients = doctor.getClients();
            doctorDTOS.add(DoctorMapper.INSTANCE.toDoctorDTO(doctor));
            doctorDTOS.get(index).setClients(clients);
            index++;
        }
        log.info("Найдено {} врачей", doctorDTOS.size());
        return ResponseEntity.ok(doctorDTOS);
    }

    /**
     * Найти врача по идентификатору.
     *
     * @param id идентификатор врача.
     * @return ResponseEntity с объектом DoctorDTO, если врач найден, или статусом 404.
     */
    public ResponseEntity<DoctorDTO> findById(int id) {
        log.info("Вызов метода findById для поиска врача с id: {}", id);
        Optional<Doctor> doctor = Optional.ofNullable(repository.findById(id));
        if (doctor.isEmpty()) {
            log.warn("Врач с id {} не найден", id);
            return ResponseEntity.notFound().build();
        }
        DoctorDTO doctorDTO = DoctorMapper.INSTANCE.toDoctorDTO(doctor.get());
        doctorDTO.setClients(doctor.get().getClients());
        log.info("Врач с id {} успешно найден", id);
        return ResponseEntity.ok(doctorDTO);
    }

    /**
     * Сохранить нового врача.
     *
     * @param doctorDTO объект DoctorDTO, который нужно сохранить.
     * @return ResponseEntity с сохраненным объектом DoctorDTO.
     */
    public ResponseEntity<DoctorDTO> save(DoctorDTO doctorDTO) {
        log.info("Вызов метода save для сохранения врача: {}", doctorDTO);
        Doctor savedDoctor = repository.save(DoctorMapper.INSTANCE.toDoctor(doctorDTO));
        log.info("Врач успешно сохранен с id: {}", savedDoctor.getId());
        return ResponseEntity.ok(DoctorMapper.INSTANCE.toDoctorDTO(savedDoctor));
    }

    /**
     * Обновить данные врача.
     *
     * @param doctorDTO объект DoctorDTO с обновленными данными.
     * @return ResponseEntity с обновленным объектом DoctorDTO, или статусом 404, если врач не найден.
     */
    public ResponseEntity<DoctorDTO> update(DoctorDTO doctorDTO) {
        log.info("Вызов метода update для обновления врача: {}", doctorDTO);
        Doctor updatedDoctor = repository.update(DoctorMapper.INSTANCE.toDoctor(doctorDTO));
        if (updatedDoctor == null) {
            log.warn("Врач с id {} не найден для обновления", doctorDTO.getDoctorId());
            return ResponseEntity.notFound().build();
        }
        log.info("Врач с id {} успешно обновлен", updatedDoctor.getId());
        return ResponseEntity.ok(DoctorMapper.INSTANCE.toDoctorDTO(updatedDoctor));
    }

    /**
     * Удалить врача по идентификатору.
     *
     * @param id идентификатор врача.
     * @return ResponseEntity со статусом 204 (успешно удалено) или 404 (не найдено).
     */
    public ResponseEntity<Void> delete(int id) {
        log.info("Вызов метода delete для удаления врача с id: {}", id);
        boolean isDelete = repository.delete(id);
        if (!isDelete) {
            log.warn("Врач с id {} не найден для удаления", id);
            return ResponseEntity.noContent().build();
        } else {
            log.info("Врач с id {} успешно удален", id);
            return ResponseEntity.notFound().build();
        }
    }
}
