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

    // Mostrar las reservas activas del usuario
    @GetMapping
    public String verMisReservas(Model model, Principal principal) {
        Usuario usuario = usuarioService.findByEmail(principal.getName());
        if (usuario == null) {
            return "redirect:/login?error=Usuario no encontrado";
        }

        List<Reserva> reservas = reservaService.obtenerReservasUsuario(usuario);
        model.addAttribute("reservas", reservas);
        return "reserva/lista";
    }

    // Paso 1: Mostrar formulario para seleccionar clase
    @GetMapping("/seleccionarClase")
    public String seleccionarClase(Model model) {
        List<Clase> clases = claseService.findAll();
        model.addAttribute("clases", clases);
        return "reserva/seleccionarClase";
    }

    // Paso 2: Mostrar horarios por clase y fecha seleccionada
    @GetMapping("/seleccionarFecha")
    public String seleccionarFecha(
            @RequestParam("claseId") Long claseId,
            @RequestParam(value = "fecha", required = false) String fechaStr,
            Model model) {

        LocalDate fecha = (fechaStr == null || fechaStr.isBlank()) ? LocalDate.now() : LocalDate.parse(fechaStr);
        Clase clase = claseService.obtenerPorId(claseId);
        List<Horario> horarios = horarioService.findByClaseAndFecha(claseId, fecha);

        model.addAttribute("clase", clase);
        model.addAttribute("fechaSeleccionada", fecha);
        model.addAttribute("horarios", horarios);
        return "reserva/seleccionarFecha";
    }

    // Paso 3: Crear reserva para un horario seleccionado (para cualquier rol)
    @PostMapping("/crear/{horarioId}")
    public String crearReserva(@PathVariable Long horarioId, Principal principal) {
        String email = principal.getName();
        Usuario usuario = usuarioService.findByEmail(email);

        if (usuario == null) {
            return "redirect:/login?error=Usuario no encontrado";
        }

        Horario horario = horarioService.obtenerPorId(horarioId);
        if (horario == null) {
            return "redirect:/reserva?error=Horario no encontrado";
        }

        try {
            reservaService.crearReserva(usuario, horario);
            return "redirect:/reserva?success=Reserva creada correctamente";
        } catch (RuntimeException e) {
            return "redirect:/reserva?error=" + e.getMessage();
        }
    }

    // Cancelar reserva (para cualquier rol)
    @PostMapping("/cancelar/{id}")
    public String cancelarReserva(@PathVariable Long id, Principal principal) {
        String email = principal.getName();
        Usuario usuario = usuarioService.findByEmail(email);

        if (usuario == null) {
            return "redirect:/login?error=Usuario no encontrado";
        }

        try {
            reservaService.cancelarReserva(id, usuario);
            return "redirect:/reserva?success=Reserva cancelada correctamente";
        } catch (RuntimeException e) {
            return "redirect:/reserva?error=" + e.getMessage();
        }
    }
}
