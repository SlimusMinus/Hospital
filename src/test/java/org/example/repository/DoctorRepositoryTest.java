package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entity.Doctor;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.datatest.DataTest.*;

@Testcontainers
@DisplayName("Тестирование CRUD-операций DoctorRepository")
class DoctorRepositoryTest extends TestContainers {

    private EntityManager entityManager;
    private DoctorRepository doctorRepository;
    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    static void setUpDatabase() {
        entityManagerFactory = Persistence.createEntityManagerFactory("test-persistence-unit");
    }

    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        doctorRepository = new DoctorRepository(entityManager);
    }

    @AfterEach
    void tearDown() {
        if (entityManager != null) {
            entityManager.close();
        }
    }

    @Test
    @DisplayName("Должен вернуть список всех докторов")
    void findAll_shouldReturnDoctorsList() {
        entityManager.getTransaction().begin();
        entityManager.persist(DOCTOR_1);
        entityManager.persist(DOCTOR_2);
        entityManager.getTransaction().commit();
        List<Doctor> doctors = doctorRepository.findAll();
        assertThat(doctors).hasSize(2);
        assertThat(doctors).extracting("firstName").contains("John", "Jane");

    }

    @Test
    @DisplayName("Должен найти доктора по идентификатору")
    void findById_shouldReturnDoctor() {
        entityManager.getTransaction().begin();
        entityManager.persist(DOCTOR_3);
        entityManager.getTransaction().commit();

        Doctor foundDoctor = doctorRepository.findById(DOCTOR_3.getId());

        assertThat(foundDoctor)
                .isNotNull()
                .extracting("firstName", "lastName", "specification")
                .containsExactly("Paul", "Walker", "Surgeon");
    }

    @Test
    @DisplayName("Должен вернуть null, если доктор не найден")
    void findById_shouldReturnNullIfNotFound() {
        Doctor foundDoctor = doctorRepository.findById(NOT_FOUND_ID);
        Assertions.assertNull(foundDoctor);
    }

    @Test
    @DisplayName("Должен обновить данные доктора")
    void update_shouldModifyDoctorDetails() {
        entityManager.getTransaction().begin();
        entityManager.persist(UPDATE_DOCTOR);
        entityManager.getTransaction().commit();

        UPDATE_DOCTOR.setLastName("Johnson");
        UPDATE_DOCTOR.setAge(42);
        UPDATE_DOCTOR.setSpecification("Rheumatologist");

        Doctor updatedDoctor = doctorRepository.update(UPDATE_DOCTOR);

        assertThat(updatedDoctor)
                .isNotNull()
                .extracting("lastName", "age", "specification")
                .containsExactly("Johnson", 42, "Rheumatologist");

        Doctor foundDoctor = entityManager.find(Doctor.class, UPDATE_DOCTOR.getId());
        assertThat(foundDoctor)
                .extracting("lastName", "age", "specification")
                .containsExactly("Johnson", 42, "Rheumatologist");
    }

    @Test
    @DisplayName("Должен сохранить нового доктора")
    void save_shouldPersistDoctor() {
        Doctor savedDoctor = doctorRepository.save(SAVE_DOCTOR);
        Doctor foundDoctor = entityManager.find(Doctor.class, savedDoctor.getId());
        assertThat(foundDoctor)
                .isNotNull()
                .extracting("firstName")
                .isEqualTo("Alice");
    }

    @Test
    @DisplayName("Должен удалить доктора по идентификатору")
    void delete_shouldRemoveDoctor() {
        entityManager.getTransaction().begin();
        entityManager.persist(DELETE_DOCTOR);
        entityManager.getTransaction().commit();
        boolean deleted = doctorRepository.delete(DELETE_DOCTOR.getId());
        assertThat(deleted).isTrue();
        Doctor foundDoctor = entityManager.find(Doctor.class, DELETE_DOCTOR.getId());
        assertThat(foundDoctor).isNull();
    }
}