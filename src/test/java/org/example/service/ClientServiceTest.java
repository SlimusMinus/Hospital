package org.example.service;

import org.example.entity.Client;
import org.example.repository.CRUDRepository;
import org.example.service.dto.ClientDTO;
import org.junit.jupiter.api.Assertions;
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
import static org.example.datatest.DataTest.CLIENT_1;
import static org.example.datatest.DataTest.CLIENT_DTO;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование ClientService")
class ClientServiceTest {
    @Mock
    private CRUDRepository<Client> repository;

    @InjectMocks
    private ClientService clientService;

    private Client client;
    private ClientDTO clientDTO;

    @BeforeEach
    void setUp() {
        client = CLIENT_1;
        clientDTO = CLIENT_DTO;
    }

    @Test
    @DisplayName("Должен вернуть список всех клиентов")
    void findAll_shouldReturnClientDTOList() {
        when(repository.findAll()).thenReturn(List.of(client));

        ResponseEntity<List<ClientDTO>> response = clientService.findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .hasSize(1)
                .first()
                .extracting(ClientDTO::getFirstName)
                .isEqualTo("John");
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Должен найти клиента по идентификатору")
    void findById_shouldReturnClientDTO() {
        when(repository.findById(1)).thenReturn(client);

        ResponseEntity<ClientDTO> response = clientService.findById(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .extracting(ClientDTO::getFirstName)
                .isEqualTo("John");
        verify(repository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Должен вернуть 404, если клиент не найден")
    void findById_shouldReturnNotFoundIfClientDoesNotExist() {
        when(repository.findById(1)).thenReturn(null);

        ResponseEntity<ClientDTO> response = clientService.findById(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(repository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Должен сохранить нового клиента")
    void save_shouldSaveClientAndReturnClientDTO() {
        when(repository.save(any(Client.class))).thenReturn(client);

        ResponseEntity<ClientDTO> response = clientService.save(clientDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .extracting(ClientDTO::getFirstName)
                .isEqualTo("John");
        verify(repository, times(1)).save(any(Client.class));
    }

    @Test
    @DisplayName("Должен обновить данные клиента")
    void update_shouldUpdateClientAndReturnClientDTO() {
        when(repository.update(any(Client.class))).thenReturn(client);

        ResponseEntity<ClientDTO> response = clientService.update(clientDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .extracting(ClientDTO::getFirstName)
                .isEqualTo("John");
        verify(repository, times(1)).update(any(Client.class));
    }

    @Test
    @DisplayName("Должен вернуть 404, если клиент не найден для обновления")
    void update_shouldReturnNotFoundIfClientDoesNotExist() {
        when(repository.update(any(Client.class))).thenReturn(null);

        ResponseEntity<ClientDTO> response = clientService.update(clientDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(repository, times(1)).update(any(Client.class));
    }

    @Test
    @DisplayName("Должен удалить клиента по идентификатору")
    void deleteById_shouldDeleteClientAndReturnNoContent() {
        when(repository.delete(1)).thenReturn(true);

        ResponseEntity<Void> response = clientService.deleteById(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(repository, times(1)).delete(1);
    }

    @Test
    @DisplayName("Должен вернуть 204, если клиент не найден для удаления")
    void deleteById_shouldReturnNotFoundIfClientDoesNotExist() {
        when(repository.delete(1)).thenReturn(false);

        ResponseEntity<Void> response = clientService.deleteById(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(repository, times(1)).delete(1);
    }
}