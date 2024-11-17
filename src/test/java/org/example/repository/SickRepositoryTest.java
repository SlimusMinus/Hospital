package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entity.Sick;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.datatest.DataTest.*;

@Testcontainers
@DisplayName("Тестирование CRUD-операций SickRepository")
class SickRepositoryTest extends TestContainers {
    private EntityManager entityManager;
    private SickRepository sickRepository;
    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    static void setUpDatabase() {
        entityManagerFactory = Persistence.createEntityManagerFactory("test-persistence-unit");
    }

    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        sickRepository = new SickRepository(entityManager);
    }

    @AfterEach
    void tearDown() {
        if (entityManager != null) {
            entityManager.close();
        }
    }

    @Test
    @DisplayName("Должен вернуть список всех болезней")
    void findAll_shouldReturnSickList() {
        entityManager.getTransaction().begin();
        entityManager.persist(SICK_3);
        entityManager.persist(SICK_2);
        entityManager.getTransaction().commit();

        List<Sick> sicks = sickRepository.findAll();

        assertThat(sicks).hasSize(5);
        assertThat(sicks).extracting(Sick::getSickName).containsExactlyInAnyOrder("Flu", "Cold", "Hot", "Flu", "Severe Flu");
    }

    @Test
    @DisplayName("Должен найти болезнь по идентификатору")
    void findById_shouldReturnSick() {
        entityManager.getTransaction().begin();
        entityManager.persist(SICK_1);
        entityManager.getTransaction().commit();

        Sick foundSick = sickRepository.findById(1);

        assertThat(foundSick).isNotNull();
        assertThat(foundSick.getSickName()).isEqualTo("Flu");
    }

    @Test
    @DisplayName("Должен вернуть null, если болезнь не найдена")
    void findById_shouldReturnNullIfNotFound() {
        Sick foundSick = sickRepository.findById(NOT_FOUND_ID);
        assertThat(foundSick).isNull();
    }

    @Test
    @DisplayName("Должен обновить данные болезни")
    void update_shouldModifySickDetails() {
        entityManager.getTransaction().begin();
        entityManager.persist(UPDATE_SICK);
        entityManager.getTransaction().commit();

        UPDATE_SICK.setSickName("Severe Flu");

        Sick updatedSick = sickRepository.update(UPDATE_SICK);

        assertThat(updatedSick).isNotNull();
        assertThat(updatedSick.getSickName()).isEqualTo("Severe Flu");

        Sick foundSick = entityManager.find(Sick.class, 1);
        assertThat(foundSick.getSickName()).isEqualTo("Flu");
    }

    @Test
    @DisplayName("Должен сохранить новую болезнь")
    void save_shouldPersistSick() {
        Sick savedSick = sickRepository.save(SAVE_SICK);

        Sick foundSick = entityManager.find(Sick.class, savedSick.getSickId());
        assertThat(foundSick).isNotNull();
        assertThat(foundSick.getSickName()).isEqualTo("Flu");
    }

    @Test
    @DisplayName("Должен удалить болезнь по идентификатору")
    void delete_shouldRemoveSick() {
        entityManager.getTransaction().begin();
        entityManager.persist(DELETE_SICK);
        entityManager.getTransaction().commit();

        boolean deleted = sickRepository.delete(1);

        assertThat(deleted).isTrue();

        Sick foundSick = entityManager.find(Sick.class, 1);
        assertThat(foundSick).isNull();
    }
}