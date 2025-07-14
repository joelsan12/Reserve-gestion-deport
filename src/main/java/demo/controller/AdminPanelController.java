package demo.controller;

import demo.model.Usuario;
import demo.service.ReservaService;
import demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminPanelController {

    @Autowired
    private UserService usuarioService;

    @Autowired
    private ReservaService reservaService;
    
    @GetMapping("/indexAdmin")
    public String mostrarIndexAdmin() {
        return "admin/indexAdmin";  // debe corresponder a src/main/resources/templates/admin/indexAdmin.html
    }

    @GetMapping("/panel")
    public String panelAdmin(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        model.addAttribute("totalReservas", reservaService.contarReservasTotales());
        model.addAttribute("reservasPorClase", reservaService.contarReservasPorClase());
        model.addAttribute("reservasPorDia", reservaService.contarReservasPorDia());
        return "admin/panel";
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "admin/usuarios/lista";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.findById(id));
        return "admin/usuarios/form";
    }

    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.save(usuario);
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return "redirect:/admin/usuarios";
    }
}
