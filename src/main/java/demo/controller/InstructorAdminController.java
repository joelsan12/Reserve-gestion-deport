package demo.controller;

import demo.model.Instructor;
import demo.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/instructores")
public class InstructorAdminController {

    @Autowired
    private InstructorService instructorService;

    @GetMapping
    public String listarInstructores(Model model, @RequestParam(required = false) String error) {
        List<Instructor> instructores = instructorService.findAll();
        model.addAttribute("instructores", instructores);
        model.addAttribute("error", error);
        return "admin/instructores/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoInstructor(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "admin/instructores/form";
    }

    @PostMapping("/guardar")
    public String guardarInstructor(@ModelAttribute Instructor instructor) {
        instructorService.save(instructor);
        return "redirect:/admin/instructores";
    }

    @GetMapping("/editar/{id}")
    public String editarInstructor(@PathVariable Long id, Model model) {
        Instructor instructor = instructorService.findById(id);
        model.addAttribute("instructor", instructor);
        return "admin/instructores/form";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarInstructor(@PathVariable Long id) {
        try {
            instructorService.deleteById(id);
        } catch (RuntimeException e) {
            // Redirigir con parámetro error para mostrar mensaje
            return "redirect:/admin/instructores?error=" + e.getMessage();
        }
        return "redirect:/admin/instructores";
    }
}
