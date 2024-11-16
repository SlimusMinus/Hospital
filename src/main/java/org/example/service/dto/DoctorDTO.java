package org.example.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) для представления врача.
 * Используется для передачи данных врача между слоями приложения.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DoctorDTO {
    @NotNull
    private int doctorId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private int age;
    @NotBlank
    private String specification;
    private List<Client> clients;
}
