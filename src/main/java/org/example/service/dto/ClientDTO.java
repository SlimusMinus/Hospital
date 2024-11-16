package org.example.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.Sick;

import java.util.List;

/**
 * Data Transfer Object (DTO) для представления клиента.
 * Используется для передачи данных клиента между слоями приложения.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    @NotNull
    private int clientId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private int age;
    private List<Sick> sicks;
}
