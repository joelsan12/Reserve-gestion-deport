package demo.controller;

import demo.model.Clase;
import demo.service.ClaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/clases")
public class ClaseAdminController {

    @Autowired
    private ClaseService claseService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clases", claseService.findAll());
        return "admin/clases/lista";  // Plantilla para mostrar listado
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("clase", new Clase());
        return "admin/clases/form";  // Formulario para crear
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Clase clase) {
        claseService.save(clase);
        return "redirect:/admin/clases";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<Clase> claseOpt = claseService.findById(id);
        if (claseOpt.isPresent()) {
            model.addAttribute("clase", claseOpt.get());
            return "admin/clases/form";  // Formulario con datos para editar
        } else {
            return "redirect:/admin/clases";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        claseService.deleteById(id);
        return "redirect:/admin/clases";
    }
}
