package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Sick;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class SickRepository implements CRUDRepository<Sick> {
    private final EntityManager entityManager;


    @Override
    public List<Sick> findAll() {
        log.info("Вызов метода findAll для получения всех клиентов");
        String jpql = "SELECT s FROM Sick s LEFT JOIN FETCH s.clients";
        List<Sick> sicks = entityManager.createQuery(jpql, Sick.class).getResultList();
        log.info("Найдено {} клиентов", sicks.size());
        return sicks;
    }

    /**
     * Найти болезнь по идентификатору.
     *
     * @param id идентификатор болезни.
     * @return Объект Sick, если найден, или null, если болезни с таким id нет.
     */
    @Override
    public Sick findById(int id) {
        log.info("Вызов метода findById для поиска болезни с id: {}", id);
        String jpql = "SELECT s FROM Sick s LEFT JOIN FETCH s.clients WHERE s.id = :id";
        try {
            Sick sick = entityManager.createQuery(jpql, Sick.class)
                    .setParameter("id", id)
                    .getSingleResult();
            log.info("Болезнь с id {} найдена", id);
            return sick;
        } catch (Exception e) {
            log.error("Ошибка при выполнении findById для id {}: {}", id, e.getMessage());
            return null;
        }
    }

    /**
     * Сохранить новую болезнь в базе данных.
     *
     * @param sick объект Sick для сохранения.
     * @return Сохранённый объект Sick.
     */
    @Override
    public Sick save(Sick sick) {
        log.info("Вызов метода save для сохранения болезни: {}", sick);
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(sick);
            transaction.commit();
            log.info("Болезнь успешно сохранена с id: {}", sick.getId());
        } catch (Exception e) {
            log.error("Ошибка при сохранении болезни: {}", e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                log.warn("Транзакция откатилась");
            }
        }
        return sick;
    }

    /**
     * Обновить данные болезни в базе данных.
     *
     * @param sick объект Sick с обновлёнными данными.
     * @return Обновленный объект Sick.
     */
    @Override
    public Sick update(Sick sick) {
        log.info("Вызов метода update для обновления болезни: {}", sick);
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Sick updatedSick = entityManager.merge(sick);
            transaction.commit();
            log.info("Болезнь успешно обновлена с id: {}", sick.getId());
            return updatedSick;
        } catch (Exception e) {
            log.error("Ошибка при обновлении болезни: {}", e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                log.warn("Транзакция откатилась");
                throw new EntityNotFoundException(e.getMessage());
            }
        }
        return null;
    }

    /**
     * Удалить болезнь по идентификатору.
     *
     * @param id идентификатор болезни.
     * @return true, если болезнь успешно удалена, иначе false.
     */
    @Override
    public boolean delete(int id) {
        log.info("Вызов метода delete для удаления болезни с id: {}", id);
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Sick sick = entityManager.find(Sick.class, id);
            if (sick != null) {
                entityManager.remove(sick);
                transaction.commit();
                log.info("Болезнь с id {} успешно удалена", id);
                return true;
            }
        } catch (Exception e) {
            log.error("Ошибка при удалении болезни с id {}: {}", id, e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                log.warn("Транзакция откатилась");
                throw new EntityNotFoundException(e.getMessage());
            }
        }
        return false;
    }
}
