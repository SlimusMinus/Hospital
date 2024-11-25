package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Client;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для выполнения операций CRUD с сущностью Client.
 * <p>
 * Этот класс использует {@link EntityManager} для выполнения операций с базой данных.
 * Все методы реализуют стандартные операции CRUD, включая сохранение, обновление, удаление,
 * а также получение всех клиентов и поиск по идентификатору.
 *
 * <p>
 * Логирование выполняется с использованием аннотации {@code @Slf4j}, что позволяет
 * отслеживать выполнение операций и обрабатывать возможные ошибки.
 * </p>
 */
@Repository
@AllArgsConstructor
@Slf4j
public class ClientRepository implements CRUDRepository<Client> {

    private final EntityManager entityManager;

    /**
     * Получить всех клиентов из базы данных.
     *
     * @return Список объектов Client с загруженными связанными данными (Sicks).
     */
    @Override
    public List<Client> findAll() {
        log.info("Вызов метода findAll для получения всех клиентов");
        String jpql = "SELECT c FROM Client c LEFT JOIN FETCH c.sicks";
        List<Client> clients = entityManager.createQuery(jpql, Client.class).getResultList();
        log.info("Найдено {} клиентов", clients.size());
        return clients;
    }

    /**
     * Найти клиента по идентификатору.
     *
     * @param id идентификатор клиента.
     * @return Объект Client, если найден, или null, если клиента с таким id нет.
     */
    @Override
    public Client findById(int id) {
        log.info("Вызов метода findById для поиска клиента с id: {}", id);
        String jpql = "SELECT c FROM Client c LEFT JOIN FETCH c.sicks WHERE c.id = :id";
        try {
            Client client = entityManager.createQuery(jpql, Client.class)
                    .setParameter("id", id)
                    .getSingleResult();
            log.info("Клиент с id {} найден", id);
            return client;
        } catch (Exception e) {
            log.error("Ошибка при выполнении findById для id {}: {}", id, e.getMessage());
            return null;
        }
    }

    /**
     * Сохранить нового клиента в базе данных.
     *
     * @param client объект Client для сохранения.
     * @return Сохраненный объект Client.
     */
    @Override
    public Client save(Client client) {
        log.info("Вызов метода save для сохранения клиента: {}", client);
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(client);
            transaction.commit();
            log.info("Клиент успешно сохранен с id: {}", client.getId());
        } catch (Exception e) {
            log.error("Ошибка при сохранении клиента: {}", e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                log.warn("Транзакция откатилась");
                throw new EntityNotFoundException(e.getMessage());
            }
        }
        return client;
    }


    /**
     * Обновить данные клиента в базе данных.
     *
     * @param client объект Client с обновленными данными.
     * @return Обновленный объект Client.
     */
    @Override
    public Client update(Client client) {
        log.info("Вызов метода update для обновления клиента: {}", client);
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(client);
            transaction.commit();
            log.info("Клиент успешно обновлен с id: {}", client.getId());
        } catch (Exception e) {
            log.error("Ошибка при обновлении клиента: {}", e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                log.warn("Транзакция откатилась");
                throw new EntityNotFoundException(e.getMessage());
            }
        }
        return client;
    }

    /**
     * Удалить клиента по идентификатору.
     *
     * @param id идентификатор клиента.
     * @return true, если клиент успешно удален, иначе false.
     */
    @Override
    public boolean delete(int id) {
        log.info("Вызов метода delete для удаления клиента с id: {}", id);
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Client client = entityManager.find(Client.class, id);
            if (client != null) {
                entityManager.remove(client);
                transaction.commit();
                log.info("Клиент с id {} успешно удален", id);
                return true;
            }
        } catch (Exception e) {
            log.error("Ошибка при удалении клиента с id {}: {}", id, e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                log.warn("Транзакция откатилась");
                throw new EntityNotFoundException(e.getMessage());
            }
        }
        return false;
    }
}
