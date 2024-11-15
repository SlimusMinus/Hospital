package org.example.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Doctor;
import org.example.repository.CRUDRepository;
import org.example.service.dto.DoctorDTO;
import org.example.service.mapper.DoctorMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class DoctorService {
    private CRUDRepository<Doctor> repository;

    public ResponseEntity<List<DoctorDTO>> findAll() {
        List<DoctorDTO> doctors = repository.findAll().stream().map(DoctorMapper.INSTANCE::toDoctorDTO).toList();
        if (doctors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(doctors);
    }

    public ResponseEntity<DoctorDTO> findById(int id) {
        Optional<Doctor> doctor = Optional.ofNullable(repository.findById(id));
        if (!doctor.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(DoctorMapper.INSTANCE.toDoctorDTO(doctor.get()));
    }

    public ResponseEntity<DoctorDTO> save(DoctorDTO doctorDTO) {
        Doctor savedDoctor = repository.save(DoctorMapper.INSTANCE.toDoctor(doctorDTO));
        return ResponseEntity.ok(DoctorMapper.INSTANCE.toDoctorDTO(savedDoctor));
    }

    public ResponseEntity<DoctorDTO> update(DoctorDTO doctorDTO) {
       /* if (!repository.existsById(parcelId)) {
            log.warn("Посылка с ID {} не найдена для обновления", parcelId);
            return ResponseEntity.notFound().build();
        }*/
        Doctor updatedDoctor = repository.save(DoctorMapper.INSTANCE.toDoctor(doctorDTO));
        return ResponseEntity.ok(DoctorMapper.INSTANCE.toDoctorDTO(updatedDoctor));
    }

    public ResponseEntity<Void> delete(int id) {
        boolean isDelete = repository.delete(id);
        if (!isDelete) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
