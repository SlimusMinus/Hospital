package org.example.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Objects;

/**
 * Класс конфигурации приложения AppConfig.
 * Конфигурирует веб-контекст, источник данных и Liquibase.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.example")
public class AppConfig {

    /**
     * Метод создает и настраивает объект YamlPropertiesFactoryBean для загрузки
     * свойств из файла application.yaml.
     *
     * @return настроенный YamlPropertiesFactoryBean
     */
    @Bean
    public YamlPropertiesFactoryBean yamlProperties() {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yaml"));
        return yaml;
    }

    /**
     * Метод создает и настраивает источник данных (DataSource) на основе
     * свойств, загруженных из файла application.yaml.
     *
     * @return настроенный DriverManagerDataSource
     * @throws NullPointerException если свойства базы данных не заданы
     */
    @Bean
    public DriverManagerDataSource dataSource() {
        YamlPropertiesFactoryBean yaml = yamlProperties();
        yaml.afterPropertiesSet();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(yaml.getObject()).getProperty("database.driver-class-name"));
        dataSource.setUrl(yaml.getObject().getProperty("database.url"));
        dataSource.setUsername(yaml.getObject().getProperty("database.username"));
        dataSource.setPassword(yaml.getObject().getProperty("database.password"));
        return dataSource;
    }

    /**
     * Метод создает и настраивает компонент SpringLiquibase для управления миграциями
     * базы данных. Использует источник данных, заданный методом dataSource(), и
     * путь к файлу changelog.
     *
     * @return настроенный SpringLiquibase
     */
    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog("classpath:db/changelog/changelog-master.xml");
        return liquibase;
    }

    /**
     * Метод создает и настраивает объект EntityManager для управления сущностями
     * и взаимодействия с базой данных через JPA.
     *
     * <p>EntityManager создается на основе фабрики сущностей (EntityManagerFactory),
     * настроенной для persistence unit с именем "hospital-db".</p>
     *
     * @return настроенный объект EntityManager
     */
    @Bean
    public EntityManager entityManager() {
        return Persistence.createEntityManagerFactory("hospital-db").createEntityManager();
    }

}
