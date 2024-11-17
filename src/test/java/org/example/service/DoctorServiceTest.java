package org.example.service;

import org.example.entity.Doctor;
import org.example.repository.CRUDRepository;
import org.example.service.dto.DoctorDTO;
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
import static org.example.datatest.DataTest.DOCTOR_1;
import static org.example.datatest.DataTest.DOCTOR_DTO;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование DoctorService")
class DoctorServiceTest {

    @Mock
    private CRUDRepository<Doctor> repository;

    @InjectMocks
    private DoctorService doctorService;

    private Doctor doctor;
    private DoctorDTO doctorDTO;

    @BeforeEach
    void setUp() {
        doctor = DOCTOR_1;
        doctorDTO = DOCTOR_DTO;
    }

    @Test
    @DisplayName("Должен вернуть список всех врачей")
    void findAll_shouldReturnDoctorDTOList() {
        when(repository.findAll()).thenReturn(List.of(doctor));

        ResponseEntity<List<DoctorDTO>> response = doctorService.findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .hasSize(1)
                .first()
                .extracting(DoctorDTO::getFirstName)
                .isEqualTo("John");
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Должен найти врача по идентификатору")
    void findById_shouldReturnDoctorDTO() {
        when(repository.findById(1)).thenReturn(doctor);

        ResponseEntity<DoctorDTO> response = doctorService.findById(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .extracting(DoctorDTO::getFirstName)
                .isEqualTo("John");
        verify(repository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Должен вернуть 404, если врач не найден")
    void findById_shouldReturnNotFoundIfDoctorDoesNotExist() {
        when(repository.findById(1)).thenReturn(null);

        ResponseEntity<DoctorDTO> response = doctorService.findById(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(repository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Должен сохранить нового врача")
    void save_shouldSaveDoctorAndReturnDoctorDTO() {
        when(repository.save(any(Doctor.class))).thenReturn(doctor);

        ResponseEntity<DoctorDTO> response = doctorService.save(doctorDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .extracting(DoctorDTO::getFirstName)
                .isEqualTo("John");
        verify(repository, times(1)).save(any(Doctor.class));
    }

    @Test
    @DisplayName("Должен обновить данные врача")
    void update_shouldUpdateDoctorAndReturnDoctorDTO() {
        when(repository.update(any(Doctor.class))).thenReturn(doctor);

        ResponseEntity<DoctorDTO> response = doctorService.update(doctorDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .extracting(DoctorDTO::getFirstName)
                .isEqualTo("John");
        verify(repository, times(1)).update(any(Doctor.class));
    }

    @Test
    @DisplayName("Должен вернуть 404, если врач не найден для обновления")
    void update_shouldReturnNotFoundIfDoctorDoesNotExist() {
        when(repository.update(any(Doctor.class))).thenReturn(null);

        ResponseEntity<DoctorDTO> response = doctorService.update(doctorDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(repository, times(1)).update(any(Doctor.class));
    }

    @Test
    @DisplayName("Должен удалить врача по идентификатору")
    void delete_shouldDeleteDoctorAndReturnNoContent() {
        when(repository.delete(1)).thenReturn(true);

        ResponseEntity<Void> response = doctorService.delete(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(repository, times(1)).delete(1);
    }

    @Test
    @DisplayName("Должен вернуть 204, если врач не найден для удаления")
    void delete_shouldReturnNoContentIfDoctorDoesNotExist() {
        when(repository.delete(1)).thenReturn(false);

        ResponseEntity<Void> response = doctorService.delete(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(repository, times(1)).delete(1);
    }

}