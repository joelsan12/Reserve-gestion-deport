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

    // Listar instructores
    @GetMapping
    public String listarInstructores(Model model) {
        List<Instructor> instructores = instructorService.findAll();
        model.addAttribute("instructores", instructores);
        return "admin/instructores/lista";
    }

    // Mostrar formulario nuevo instructor
    @GetMapping("/nuevo")
    public String nuevoInstructor(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "admin/instructores/form";
    }

    // Guardar instructor nuevo o editado
    @PostMapping("/guardar")
    public String guardarInstructor(@ModelAttribute Instructor instructor) {
        instructorService.save(instructor);
        return "redirect:/admin/instructores";
    }

    // Mostrar formulario editar instructor
    @GetMapping("/editar/{id}")
    public String editarInstructor(@PathVariable Long id, Model model) {
        Instructor instructor = instructorService.findById(id);
        model.addAttribute("instructor", instructor);
        return "admin/instructores/form";
    }

    // Eliminar instructor
    @PostMapping("/eliminar/{id}")
    public String eliminarInstructor(@PathVariable Long id) {
        instructorService.deleteById(id);
        return "redirect:/admin/instructores";
    }
}
