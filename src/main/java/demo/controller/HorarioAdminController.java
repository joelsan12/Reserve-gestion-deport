package demo.controller;

import demo.model.Clase;
import demo.model.Horario;
import demo.model.Instructor;
import demo.service.ClaseService;
import demo.service.HorarioService;
import demo.service.InstructorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/horarios")
public class HorarioAdminController {

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private ClaseService claseService;

    @Autowired
    private InstructorService instructorService;

    // Listar todos los horarios
    @GetMapping
    public String listarHorarios(Model model) {
        List<Horario> horarios = horarioService.obtenerTodos();
        model.addAttribute("horarios", horarios);
        return "admin/horarios/lista";
    }

    // Mostrar formulario para crear nuevo horario
    @GetMapping("/nuevo")
    public String nuevoHorario(Model model) {
        model.addAttribute("horario", new Horario());
        model.addAttribute("clases", claseService.findAll());              // Traer todas las clases
        model.addAttribute("instructores", instructorService.findAll());    // Traer todos los instructores
        return "admin/horarios/form";
    }

    // Mostrar formulario para editar un horario existente
    @GetMapping("/editar/{id}")
    public String editarHorario(@PathVariable Long id, Model model) {
        Horario horario = horarioService.obtenerPorId(id);
        if (horario == null) {
            return "redirect:/admin/horarios";
        }
        model.addAttribute("horario", horario);
        model.addAttribute("clases", claseService.findAll());
        model.addAttribute("instructores", instructorService.findAll());
        return "admin/horarios/form";
    }

    // Guardar horario nuevo o editado
    @PostMapping("/guardar")
    public String guardarHorario(@ModelAttribute Horario horario) {

        // Cargar las entidades completas
        Clase clase = claseService.obtenerPorId(horario.getClase().getId());
        Instructor instructor = instructorService.findById(horario.getInstructor().getId());

        // Setear las entidades en el horario antes de guardar
        horario.setClase(clase);
        horario.setInstructor(instructor);

        horarioService.guardar(horario);
        return "redirect:/admin/horarios";
    }

    // Eliminar un horario
    @PostMapping("/eliminar/{id}")
    public String eliminarHorario(@PathVariable Long id) {
        horarioService.eliminar(id);
        return "redirect:/admin/horarios";
    }
}
