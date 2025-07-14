package demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import demo.model.Horario;
import demo.service.HorarioService;

@Controller
public class HorarioController {

    @Autowired
    private HorarioService horarioService; // o repositorio

    @GetMapping("/horarios")
    public String mostrarHorarios(Model model) {
        List<Horario> listaHorarios = horarioService.obtenerTodos(); // o repositorio.findAll();
        model.addAttribute("horarios", listaHorarios);
        return "horarios"; // nombre del archivo Thymeleaf sin extensión
    }
}
