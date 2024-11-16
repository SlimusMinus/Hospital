package org.example.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Класс, представляющий сущность доктора в системе.
 *
 * <p>Содержит информацию о докторе, включая его имя, фамилию, возраст, специальность,
 * а также список клиентов, прикрепленных к доктору.</p>
 *
 * <p>Связи:
 * <ul>
 *     <li>Один-ко-многим с сущностью {@link Client}: Доктор может быть связан с несколькими клиентами.</li>
 * </ul>
 * </p>
 *
 * <p>Использует аннотации JPA для сопоставления с таблицей "doctors" в базе данных, а также
 * аннотации Lombok для автоматической генерации методов геттеров, сеттеров, конструктора и equals/hashCode.</p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private int doctorId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "age")
    private int age;
    @Column(name = "specification")
    private String specification;
    @OneToMany(mappedBy = "doctor")
    @JsonManagedReference
    private List<Client> clients;
}
