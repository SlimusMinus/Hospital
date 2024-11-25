package org.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
 * Класс, представляющий сущность клиента в системе.
 *
 * <p>Содержит информацию о клиенте, включая его имя, фамилию, возраст,
 * а также связь с доктором и списком болезней.</p>
 *
 * <p>Связи:
 * <ul>
 *     <li>Многие-к-одному с сущностью {@link Doctor}: Клиент связан с одним доктором.</li>
 *     <li>Многие-ко-многим с сущностью {@link Sick}: Клиент может иметь несколько болезней.</li>
 * </ul>
 * </p>
 *
 * <p>Использует аннотации JPA для сопоставления с таблицей "clients" в базе данных, а также
 * аннотации Lombok для автоматической генерации методов геттеров, сеттеров, конструктора и equals/hashCode.</p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "client")
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "client_id")
  private int id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "age")
  private int age;

  @ManyToOne(cascade = CascadeType.DETACH)
  @JoinColumn(name = "doctor_id")
  @JsonBackReference
  private Doctor doctor;

  @ManyToMany(cascade = CascadeType.DETACH)
  @JsonIgnoreProperties("clients")
  @JoinTable(
      name = "client_sick",
      joinColumns = @JoinColumn(name = "client_id"),
      inverseJoinColumns = @JoinColumn(name = "sick_id")
  )
  @ToString.Exclude
  private List<Sick> sicks;

  public Client(String firstName, String lastName, int age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Client client = (Client) o;
    return id == client.id;
  }

  @Override
  public int hashCode() {
    return id == 0 ? System.identityHashCode(this) : Objects.hashCode(id);
  }
}