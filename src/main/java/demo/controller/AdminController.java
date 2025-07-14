package demo.controller;

import demo.model.Clase;
import demo.service.ClaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ClaseService claseService;

    @GetMapping
    public String adminPanel(Model model) {
        List<Clase> clases = claseService.findAll();
        model.addAttribute("clases", clases);
        return "admin";
    }

    @PostMapping("/clase")
    public String addClase(@ModelAttribute Clase clase) {
        claseService.save(clase);
        return "redirect:/admin";
    }
}
