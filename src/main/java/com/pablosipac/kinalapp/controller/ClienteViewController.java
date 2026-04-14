package com.pablosipac.kinalapp.controller;
import com.pablosipac.kinalapp.entity.Cliente;
import com.pablosipac.kinalapp.service.IClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@Controller
@RequestMapping("/vistas/clientes")
public class ClienteViewController {

    private final IClienteService clienteService;

    public ClienteViewController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @GetMapping
    public String listar(Model model) {
        // Model es como una "mochila" donde pones datos
        // para que la plantilla HTML los use
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("titulo", "Lista de Clientes");
        return "clientes/lista"; // ← busca el archivo templates/clientes/lista.html
    }

    //Recibe y Guarda el formulario del cliente
    @GetMapping("/nuevo")
    public  String mostrarFormularioNuevo(Model model){
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("titulo", "Nuevo Cliente");
        return "clientes/formulario";
    }
    //Recibe y Guarda el cliente
    @PostMapping("/guarda")
    public  String guardar(@ModelAttribute Cliente cliente, Model model){
        //@ModelAttribute toma los campos  del formulario HTML y los convierte al objeto cliente automaticamente
        try {
            clienteService.guardar(cliente);// redirige a la lista
            return "redirect:/vista/clientes";
        }catch (IllegalArgumentException e){
            model.addAttribute("error", e.getMessage());
            model.addAttribute("cliente", cliente);
            return "clientes/formulario";

        }

    }
    //Muestra el formulario con datos del cliente para editar
    @GetMapping("/editar/{dpi}")
    public String mostrarFormularioEditar(@PathVariable String dpi, Model model){
        return clienteService.buscarPorDPI(dpi)
                .map(cliente -> {
                model.addAttribute("cliente", cliente);
                model.addAttribute("Titulo", "Editar Cliente" );
                return "clientes/formulario"; // es el mismo formulario
                })
                .orElse("redirect:/vista/clientes");
    }

    //Elimina y redirige a la lista
    @GetMapping("/eliminar/{dpi}")
    public String eliminar (@PathVariable String dpi){
        if (clienteService.existePorDPI(dpi)){
            clienteService.eliminar(dpi);
        }
        return "redirect:/vista/clientes";
    }



}