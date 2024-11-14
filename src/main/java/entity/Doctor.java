package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Doctor {
    @Id
    private int doctorId;
    private String firstName;
    private String lastName;
    private int age;
    private String specification;
}
