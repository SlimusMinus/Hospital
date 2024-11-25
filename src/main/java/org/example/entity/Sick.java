package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import lombok.Setter;
import lombok.ToString;

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
@Getter
@Setter
@Entity
@Table(name = "sick")
public class Sick {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sick_id")
    private int id;

    @Column(name = "sick_name")
    private String sickName;

    @Column(name = "stage_sick")
    private String stageSick;

    @ManyToMany(mappedBy = "sicks", cascade = CascadeType.DETACH)
    @JsonIgnoreProperties("sicks")
    @ToString.Exclude
    private List<Client> clients;

    public Sick(String sickName, String stageSick) {
        this.sickName = sickName;
        this.stageSick = stageSick;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sick sick = (Sick) o;
        return id == sick.id;
    }

    @Override
    public int hashCode() {
        return id == 0 ? System.identityHashCode(this) : Objects.hashCode(id);
    }
}
