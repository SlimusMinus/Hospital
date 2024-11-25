package org.example.repository;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@SuppressWarnings("resource")
public abstract class TestContainers {
    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:15.3")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    static {
        POSTGRES_CONTAINER.start();
        System.setProperty("jakarta.persistence.jdbc.url", POSTGRES_CONTAINER.getJdbcUrl());
        System.setProperty("jakarta.persistence.jdbc.user", POSTGRES_CONTAINER.getUsername());
        System.setProperty("jakarta.persistence.jdbc.password", POSTGRES_CONTAINER.getPassword());
    }

    public static PostgreSQLContainer<?> getContainer() {
        return POSTGRES_CONTAINER;
    }
}
