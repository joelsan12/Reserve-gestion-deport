package demo.controller;

import demo.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.format.DateTimeFormatter;
import java.util.Map;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class EstadisticasController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/admin/estadisticas")
    public String mostrarEstadisticas(Model model) {
        Map<String, Long> reservasPorClase = reservaService.contarReservasPorClase();
        Map<java.time.LocalDate, Long> reservasPorDia = reservaService.contarReservasPorDia();

        model.addAttribute("reservasPorClase", reservasPorClase);
        model.addAttribute("reservasPorDia", reservasPorDia);
        model.addAttribute("dateFormatter", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return "admin/estadisticas";
    }
}
