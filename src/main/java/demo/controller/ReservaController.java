package demo.controller;

import demo.model.Clase;
import demo.model.Horario;
import demo.model.Reserva;
import demo.model.Usuario;
import demo.service.ClaseService;
import demo.service.HorarioService;
import demo.service.ReservaService;
import demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private UserService usuarioService;

    @Autowired
    private ClaseService claseService;

    // 1. Mostrar lista de clases para elegir
    @GetMapping("/clases")
    public String listarClases(Model model) {
        List<Clase> clases = claseService.findAll();
        model.addAttribute("clases", clases);
        return "reserva/clases";
    }

    // 2. Mostrar horarios disponibles para una clase y fecha
    @GetMapping("/clases/{claseId}/horarios")
    public String horariosPorClase(
            @PathVariable Long claseId,
            @RequestParam(value = "fecha", required = false) String fechaStr,
            Model model) {

        LocalDate fecha = (fechaStr == null || fechaStr.isBlank()) ? LocalDate.now() : LocalDate.parse(fechaStr);
        List<Horario> horarios = horarioService.findByClaseAndFecha(claseId, fecha);
        model.addAttribute("horarios", horarios);
        model.addAttribute("claseId", claseId);
        model.addAttribute("fechaSeleccionada", fecha);
        return "reserva/horarios";
    }

    // 3. Crear reserva para un horario seleccionado
    @PostMapping("/crear/{horarioId}")
    public String crearReserva(@PathVariable Long horarioId, Principal principal, Model model) {
        Usuario usuario = usuarioService.findByEmail(principal.getName());
        Horario horario = horarioService.obtenerPorId(horarioId);
        if (horario == null) {
            model.addAttribute("error", "Horario no encontrado");
            return "error";
        }

        try {
            reservaService.crearReserva(usuario, horario);
            return "redirect:/reserva?exito";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}
