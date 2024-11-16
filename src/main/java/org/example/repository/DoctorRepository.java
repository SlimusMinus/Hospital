package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Doctor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для выполнения операций CRUD с сущностью Doctor.
 * <p>
 * Этот класс использует {@link EntityManager} для выполнения операций с базой данных.
 * Все методы реализуют стандартные операции CRUD, включая сохранение, обновление, удаление,
 * а также получение всех докторов и поиск по идентификатору.
 *
 * <p>
 * Логирование выполняется с использованием аннотации {@code @Slf4j}, что позволяет
 * отслеживать выполнение операций и обрабатывать возможные ошибки.
 * </p>
 */
@Repository
@AllArgsConstructor
@Slf4j
public class DoctorRepository implements CRUDRepository<Doctor> {

    private final EntityManager entityManager;

    /**
     * Получить всех докторов из базы данных.
     *
     * @return Список объектов Doctor с загруженными связанными данными (Clients).
     */
    @Override
    public List<Doctor> findAll() {
        log.info("Вызов метода findAll для получения всех докторов");
        String jpql = "SELECT d FROM Doctor d LEFT JOIN FETCH d.clients";
        List<Doctor> doctors = entityManager.createQuery(jpql, Doctor.class).getResultList();
        log.info("Найдено {} докторов", doctors.size());
        return doctors;
    }

    /**
     * Найти доктора по идентификатору.
     *
     * @param id идентификатор доктора.
     * @return Объект Doctor, если найден, иначе null.
     */
    @Override
    public Doctor findById(int id) {
        log.info("Вызов метода findById для поиска доктора с id: {}", id);
        try {
            Doctor doctor = entityManager.find(Doctor.class, id);
            if (doctor != null) {
                log.info("Доктор с id {} найден", id);
            } else {
                log.warn("Доктор с id {} не найден", id);
            }
            return doctor;
        } catch (Exception e) {
            log.error("Ошибка при выполнении findById для id {}: {}", id, e.getMessage());
            return null;
        }
    }

    /**
     * Сохранить нового доктора в базе данных.
     *
     * @param doctor объект Doctor для сохранения.
     * @return Сохраненный объект Doctor.
     */
    @Override
    public Doctor save(Doctor doctor) {
        log.info("Вызов метода save для сохранения доктора: {}", doctor);
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(doctor);
            transaction.commit();
            log.info("Доктор успешно сохранен с id: {}", doctor.getDoctorId());
        } catch (Exception e) {
            log.error("Ошибка при сохранении доктора: {}", e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                log.warn("Транзакция откатилась");
            }
        }
        return doctor;
    }

    /**
     * Обновить данные доктора в базе данных.
     *
     * @param doctor объект Doctor с обновленными данными.
     * @return Обновленный объект Doctor.
     */
    @Override
    public Doctor update(Doctor doctor) {
        log.info("Вызов метода update для обновления доктора: {}", doctor);
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(doctor);
            transaction.commit();
            log.info("Доктор успешно обновлен с id: {}", doctor.getDoctorId());
        } catch (Exception e) {
            log.error("Ошибка при обновлении доктора: {}", e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                log.warn("Транзакция откатилась");
            }
        }
        return doctor;
    }

    /**
     * Удалить доктора по идентификатору.
     *
     * @param id идентификатор доктора.
     * @return true, если доктор успешно удален, иначе false.
     */
    @Override
    public boolean delete(int id) {
        log.info("Вызов метода delete для удаления доктора с id: {}", id);
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Doctor doctor = entityManager.find(Doctor.class, id);
            if (doctor != null) {
                entityManager.remove(doctor);
                transaction.commit();
                log.info("Доктор с id {} успешно удален", id);
                return true;
            }
        } catch (Exception e) {
            log.error("Ошибка при удалении доктора с id {}: {}", id, e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                log.warn("Транзакция откатилась");
            }
        }
        return false;
    }
}