package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
