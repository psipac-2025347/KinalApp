package com.pablosipac.kinalapp.controller;

import com.pablosipac.kinalapp.entity.Usuario;
import com.pablosipac.kinalapp.service.IUsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vista")
public class LoginViewController {

    private final IUsuarioService usuarioService;

    public LoginViewController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username, @RequestParam String password, Model model){
        //Se busca si el usuario coincide con alguno
        boolean encontrado = usuarioService.listarTodos().stream()
                .anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
        if (encontrado) {
            return "redirect:/vista/menu";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login";
        }
    }

    @GetMapping("/menu")
    public String mostrarMenu() {
        return "menu";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam String rol, Model model) {
        try {
            Usuario nuevo = new Usuario();
            nuevo.setUsername(username);
            nuevo.setPassword(password);
            nuevo.setEmail(email);
            nuevo.setRol(rol);
            nuevo.setEstado(1L);
            usuarioService.guardar(nuevo);
            return "redirect:/vista/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }
}
