package demo.controller;

import demo.model.Usuario;
import demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";  // nombre del archivo registro.html en templates
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute Usuario usuario) {
        userService.registerUser(usuario);
        return "redirect:/login";
    }
}
