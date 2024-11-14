package controller;

import entity.Doctor;
import org.springframework.web.bind.annotation.*;
import service.DoctorService;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    private DoctorService doctorService;

    @GetMapping
    public List<Doctor> findAll(){
        return doctorService.findAll();
    }

    @GetMapping("{/id}")
    public Doctor findById(@PathVariable int id){
        return doctorService.findById(id);
    }

    @PostMapping
    public Doctor create(@RequestBody Doctor doctor){
        return doctorService.save(doctor);
    }

    @DeleteMapping
    public boolean delete(@RequestBody Doctor doctor){
        return doctorService.delete(doctor);
    }
}
