package com.smartattendance;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class AttendanceController {

    private final AttendanceRepository attendanceRepository;

    public AttendanceController(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @GetMapping("/attendance")
public String showAttendance(Model model) {

    model.addAttribute("attendance", new Attendance());

    var records = attendanceRepository.findAll();

    model.addAttribute("records",records);
    model.addAttribute("total",records.size());

    model.addAttribute("present", records.stream()
        .filter(a -> "Present".equalsIgnoreCase(a.getStatus()))
        .count());

model.addAttribute("absent", records.stream()
        .filter(a -> "Absent".equalsIgnoreCase(a.getStatus()))
        .count());

    return "attendance";
}


    @PostMapping("/attendance/save")
    public String saveAttendance(
            @ModelAttribute("attendance") Attendance attendance) {
        attendanceRepository.save(attendance);
        return "redirect:/attendance";
    }

    @GetMapping("/reports")
    public String showReports(Model model){
        model.addAttribute("records",attendanceRepository.findAll());
        return "reports";
    }
}

