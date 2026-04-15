package com.pablosipac.kinalapp.controller;

import com.pablosipac.kinalapp.entity.Cliente;
import com.pablosipac.kinalapp.entity.Usuario;
import com.pablosipac.kinalapp.service.IUsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vista/usuarios")
public class UsuarioViewController {

    private final IUsuarioService usuarioService;

    public UsuarioViewController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar (Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("titulo", "Lista de Usuarios");
        return  "usuarios/lista";
    }

    //Recibe y guarda el formulario del cliente
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo (Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("titulo", "Nuevo Usuario");
        return "usuarios/formulario";
    }

    @PostMapping("/guardar")
    public String guardar (@ModelAttribute Usuario usuario, Model model) {
    try {
        usuarioService.guardar(usuario);
        return "redirect:/vista/usuarios";
    } catch (IllegalArgumentException e) {
        model.addAttribute("error", e.getMessage());
        model.addAttribute("usuario", usuario );
    }
    return "usuarios/formulario";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Usuario usuario, Model model) {
        try {
            usuarioService.actualizar(id, usuario);
            return "redirect:/vista/usuarios";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("usuario", usuario);
            return "usuarios/formulario";
        }
    }

    @GetMapping("/eliminar/{codigoUsuario}")
    public String eliminar(@PathVariable Long codigoUsuario, Model model){
        if (usuarioService.existePorcodigoUsuario(codigoUsuario)) {
            usuarioService.eliminar(codigoUsuario);
        }
        return "redirect:/vista/usuarios";
    }
}
