package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entity.Client;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.datatest.DataTest.*;

@Testcontainers
@DisplayName("Тестирование CRUD-операций ClientRepository")
class ClientRepositoryTest extends TestContainers {

    private EntityManager entityManager;
    private ClientRepository clientRepository;
    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    static void setUpDatabase() {
        entityManagerFactory = Persistence.createEntityManagerFactory("test-persistence-unit");
    }

    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        clientRepository = new ClientRepository(entityManager);
    }

    @AfterEach
    void tearDown() {
        if (entityManager != null) {
            entityManager.close();
        }
    }

    @Test
    @DisplayName("Должен вернуть список всех клиентов")
    void findAll_shouldReturnClientsList() {
        entityManager.getTransaction().begin();
        entityManager.persist(CLIENT_1);
        entityManager.persist(CLIENT_2);
        entityManager.getTransaction().commit();

        List<Client> clients = clientRepository.findAll();

        assertThat(2).isEqualTo(clients.size());
        assertThat(clients.stream().anyMatch(c -> c.getFirstName().equals("John"))).isTrue();
        assertThat(clients.stream().anyMatch(c -> c.getFirstName().equals("Jane"))).isTrue();
    }

    @Test
    @DisplayName("Должен найти клиента по идентификатору")
    void findById_shouldReturnClient() {
        entityManager.getTransaction().begin();
        entityManager.persist(CLIENT_3);
        entityManager.getTransaction().commit();

        Client foundClient = clientRepository.findById(CLIENT_3.getClientId());

        assertThat(foundClient).isNotNull();
        assertThat("Alice").isEqualTo(foundClient.getFirstName());
        assertThat("Brown").isEqualTo(foundClient.getLastName());

    }

    @Test
    @DisplayName("Должен вернуть null, если клиент не найден")
    void findById_shouldReturnNullIfNotFound() {
        Client foundClient = clientRepository.findById(NOT_FOUND_ID);
        assertThat(foundClient).isNull();
    }

    @Test
    @DisplayName("Должен обновить данные клиента")
    void update_shouldModifyClientDetails() {
        entityManager.getTransaction().begin();
        entityManager.persist(UPDATE_CLIENT);
        entityManager.getTransaction().commit();

        UPDATE_CLIENT.setFirstName("Pol");
        UPDATE_CLIENT.setLastName("Smith");

        Client updatedClient = clientRepository.update(UPDATE_CLIENT);
        assertThat(updatedClient).isNotNull();
        assertThat("Pol").isEqualTo(updatedClient.getFirstName());
        assertThat("Smith").isEqualTo(updatedClient.getLastName());

        Client foundClient = entityManager.find(Client.class, UPDATE_CLIENT.getClientId());
        assertThat("Pol").isEqualTo(foundClient.getFirstName());
        assertThat("Smith").isEqualTo(foundClient.getLastName());
    }

    @Test
    @DisplayName("Должен сохранить нового клиента")
    void save_shouldPersistClient() {
        Client savedClient = clientRepository.save(SAVE_CLIENT);

        Client foundClient = entityManager.find(Client.class, savedClient.getClientId());
        Assertions.assertNotNull(foundClient);
        assertThat(foundClient).isNotNull();
        assertThat("Tom").isEqualTo(foundClient.getFirstName());
    }

    @Test
    @DisplayName("Должен удалить клиента по идентификатору")
    void delete_shouldRemoveClient() {
        entityManager.getTransaction().begin();
        entityManager.persist(DELETE_CLIENT);
        entityManager.getTransaction().commit();

        boolean deleted = clientRepository.delete(DELETE_CLIENT.getClientId());

        assertThat(deleted).isTrue();
        Client foundClient = entityManager.find(Client.class, DELETE_CLIENT.getClientId());
        assertThat(foundClient).isNull();
    }
}