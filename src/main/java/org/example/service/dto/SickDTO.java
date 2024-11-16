package org.example.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.Client;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SickDTO {
    @NotNull
    private int sickId;
    @NotBlank
    private String sickName;
    @NotBlank
    private String stageSick;
    private List<Client> clients;
}
