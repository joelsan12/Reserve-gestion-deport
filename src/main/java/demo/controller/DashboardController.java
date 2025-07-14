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
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private ClaseService claseService;

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private InstructorService instructorService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Clase> clases = claseService.findAll(); // Asegúrate que ClaseService tenga findAll() o cambia a obtenerTodos()
        List<Horario> horarios = horarioService.obtenerTodos(); // Cambiado aquí para que use tu método correcto
        List<Instructor> instructores = instructorService.findAll(); // Igual que clase, valida método

        model.addAttribute("clases", clases);
        model.addAttribute("horarios", horarios);
        model.addAttribute("instructores", instructores);

        return "dashboard";
    }

}
