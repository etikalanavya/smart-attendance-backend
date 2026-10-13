package com.smartattendance;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students")
    public String students(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "students";
    }

    @PostMapping("/students/add")
    public String addStudent(
            @RequestParam String rollNumber,
            @RequestParam String name,
            @RequestParam String department,
            RedirectAttributes redirectAttributes) {

        Student student = new Student();
        student.setRollNumber(rollNumber);
        student.setName(name);
        student.setDepartment(department);

        studentRepository.save(student);
        redirectAttributes.addFlashAttribute(
                "message", "Student added successfully!");

        return "redirect:/students";
    }
}
