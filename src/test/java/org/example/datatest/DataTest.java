package org.example.datatest;

import org.example.entity.Client;
import org.example.entity.Doctor;
import org.example.entity.Sick;
import org.example.service.dto.ClientDTO;
import org.example.service.dto.DoctorDTO;

public class DataTest {
    public static final Doctor DOCTOR_1 = new Doctor("John", "Doe", 54, "Cardiologist");
    public static final Doctor DOCTOR_2 = new Doctor("Jane", "Smith", 39, "Neurologist");
    public static final Doctor DOCTOR_3 = new Doctor("Paul", "Walker", 50, "Surgeon");
    public static final Doctor UPDATE_DOCTOR = new Doctor("Emily", "Clark", 41, "Orthopedic");
    public static final Doctor SAVE_DOCTOR = new Doctor("Alice", "Johnson", 33, "Pediatrician");
    public static final Doctor DELETE_DOCTOR = new Doctor("Mark", "Brown", 45, "Dermatologist");
    public static final Client CLIENT_1 = new Client("John", "Doe", 25);
    public static final Client CLIENT_2 = new Client("Jane", "Smith", 44);
    public static final Client CLIENT_3 = new Client("Alice", "Brown", 53);
    public static final Client UPDATE_CLIENT = new Client("Michael", "Johnson", 39);
    public static final Client SAVE_CLIENT = new Client("Tom", "White", 50);
    public static final Client DELETE_CLIENT = new Client("Sara", "Miller", 19);
    public static final ClientDTO CLIENT_DTO = new ClientDTO("John", "Doe", 25);
    public static final DoctorDTO DOCTOR_DTO = new DoctorDTO("John", "Doe", 54, "Cardiologist");
    public static final Sick SICK_1 = new Sick("Flu", "First");
    public static final Sick SICK_2 = new Sick("Cold", "First");
    public static final Sick SICK_3 = new Sick("Hot", "First");

    public static final Sick UPDATE_SICK = new Sick("Cold", "First");
    public static final Sick SAVE_SICK = new Sick("Flu", "First");
    public static final Sick DELETE_SICK = new Sick("Flu", "First");

    public static final int NOT_FOUND_ID = 999;
}
