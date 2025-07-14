package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import demo.service.InstructorService;

@Controller
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @GetMapping("/instructores")
    public String listarInstructores(Model model) {
        model.addAttribute("instructores", instructorService.findAll());
        return "instructores";  
    }
}
