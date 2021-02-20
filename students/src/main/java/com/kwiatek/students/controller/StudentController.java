package com.kwiatek.students.controller;


import com.kwiatek.students.model.Student;
import com.kwiatek.students.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;



@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping()
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Student addStudent(@RequestBody @Valid Student student) {
        return studentRepository.save(student);
    }

    //Jeśli istnieje to pobierz
    //Jeśli nie to się zbuduj
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return studentRepository.findById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    //jeśli istnieje to usun i zwróc odpowiedź
    //jeśli nie to zbuduj
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return ResponseEntity.ok().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Podmień wszystkie dane studenta o id id
    @PutMapping("/{id}")
    public ResponseEntity<Student> putStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentRepository.findById(id)
                .map(studentFromDb -> {
                    studentFromDb.setFirstName(student.getFirstName());
                    studentFromDb.setLastName(student.getLastName());
                    studentFromDb.setEmail(student.getEmail());
                    studentRepository.save(studentFromDb);
                    return ResponseEntity.ok().body(studentFromDb);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Podmienia tylko wybrane pola w odróżnieniu od put
    @PatchMapping("/{id}")
    public ResponseEntity<Student> patchStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentRepository.findById(id)
                .map(studentFromDb -> {
                    if (!StringUtils.isEmpty(student.getFirstName())) {
                        studentFromDb.setFirstName(student.getFirstName());
                    }
                    if (!StringUtils.isEmpty(student.getLastName())) {
                        studentFromDb.setLastName(student.getLastName());
                    }
                    studentRepository.save(studentFromDb);
                    return ResponseEntity.ok().body(studentFromDb);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }



}
