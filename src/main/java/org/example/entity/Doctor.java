package org.example.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import lombok.Setter;
import lombok.ToString;

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
@Getter
@Setter
@Entity
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "age")
    private int age;
    @Column(name = "specification")
    private String specification;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.DETACH)
    @JsonManagedReference
    @ToString.Exclude
    private List<Client> clients;

    public Doctor(String firstName, String lastName, int age, String specification) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.specification = specification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Doctor doctor = (Doctor) o;
        return id == doctor.id;
    }

    @Override
    public int hashCode() {
        return id == 0 ? System.identityHashCode(this) : Objects.hashCode(id);
    }
}
