package demo.controller;

import demo.model.Horario;
import demo.service.HorarioService;
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
        return "admin/horarios/form";
    }

    // Mostrar formulario para editar un horario existente
    @GetMapping("/editar/{id}")
    public String editarHorario(@PathVariable Long id, Model model) {
        Horario horario = horarioService.obtenerPorId(id);
        if (horario == null) {
            // Redirige a la lista si no existe el horario
            return "redirect:/admin/horarios";
        }
        model.addAttribute("horario", horario);
        return "admin/horarios/form";
    }

    // Guardar horario nuevo o editado
    @PostMapping("/guardar")
    public String guardarHorario(@ModelAttribute Horario horario) {
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
