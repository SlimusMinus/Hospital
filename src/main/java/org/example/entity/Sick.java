package org.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий сущность болезни в системе.
 *
 * <p>Содержит информацию о болезни, включая ее название и стадию.
 * Также содержит связь с клиентами, у которых зарегистрирована эта болезнь.</p>
 *
 * <p>Связи:
 * <ul>
 *     <li>Многие-ко-многим с сущностью {@link Client}: Болезнь может быть связана с несколькими клиентами.</li>
 * </ul>
 * </p>
 *
 * <p>Использует аннотации JPA для сопоставления с таблицей "sicks" в базе данных, а также
 * аннотации Lombok для автоматической генерации методов геттеров, сеттеров, конструктора и equals/hashCode.</p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "sicks")
public class Sick {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sick_id")
    private int sickId;

    @Column(name = "sick_name")
    private String sickName;

    @Column(name = "stage_sick")
    private String stageSick;

    @ManyToMany(mappedBy = "sicks")
    @JsonBackReference
    private List<Client> clients = new ArrayList<>();
}
