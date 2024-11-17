package org.example.service;

import org.example.entity.Sick;
import org.example.repository.CRUDRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.datatest.DataTest.SICK_1;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование SickService")
class SickServiceTest {
    @Mock
    private CRUDRepository<Sick> repository;

    @InjectMocks
    private SickService sickService;

    private Sick sick;

    @BeforeEach
    void setUp() {
        sick = SICK_1;
    }

    @Test
    @DisplayName("Должен вернуть список всех болезней")
    void findAll_shouldReturnSickList() {
        when(repository.findAll()).thenReturn(List.of(sick));

        ResponseEntity<List<Sick>> response = sickService.findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .hasSize(1)
                .first()
                .extracting(Sick::getSickName)
                .isEqualTo("Flu");
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Должен найти болезнь по идентификатору")
    void findById_shouldReturnSick() {
        when(repository.findById(1)).thenReturn(sick);

        ResponseEntity<Sick> response = sickService.findById(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .extracting(Sick::getSickName)
                .isEqualTo("Flu");
        verify(repository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Должен вернуть 404, если болезнь не найдена")
    void findById_shouldReturnNotFoundIfSickDoesNotExist() {
        when(repository.findById(1)).thenReturn(null);

        ResponseEntity<Sick> response = sickService.findById(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(repository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Должен сохранить новую болезнь")
    void save_shouldSaveSick() {
        when(repository.save(any(Sick.class))).thenReturn(sick);

        ResponseEntity<Sick> response = sickService.save(sick);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .extracting(Sick::getSickName)
                .isEqualTo("Flu");
        verify(repository, times(1)).save(any(Sick.class));
    }

    @Test
    @DisplayName("Должен обновить данные болезни")
    void update_shouldUpdateSick() {
        when(repository.update(any(Sick.class))).thenReturn(sick);

        ResponseEntity<Sick> response = sickService.update(sick);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .extracting(Sick::getSickName)
                .isEqualTo("Flu");
        verify(repository, times(1)).update(any(Sick.class));
    }

    @Test
    @DisplayName("Должен вернуть 404, если болезнь не найдена для обновления")
    void update_shouldReturnNotFoundIfSickDoesNotExist() {
        when(repository.update(any(Sick.class))).thenReturn(null);

        ResponseEntity<Sick> response = sickService.update(sick);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(repository, times(1)).update(any(Sick.class));
    }

    @Test
    @DisplayName("Должен удалить болезнь по идентификатору")
    void deleteById_shouldDeleteSick() {
        when(repository.delete(1)).thenReturn(true);

        ResponseEntity<Void> response = sickService.deleteById(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(repository, times(1)).delete(1);
    }

    @Test
    @DisplayName("Должен вернуть 404, если болезнь не найдена для удаления")
    void deleteById_shouldReturnNotFoundIfSickDoesNotExist() {
        when(repository.delete(1)).thenReturn(false);

        ResponseEntity<Void> response = sickService.deleteById(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(repository, times(1)).delete(1);
    }
}