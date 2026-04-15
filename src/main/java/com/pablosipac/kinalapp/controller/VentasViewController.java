package com.pablosipac.kinalapp.controller;

import com.pablosipac.kinalapp.entity.Venta;
import com.pablosipac.kinalapp.service.ClienteService;
import com.pablosipac.kinalapp.service.IClienteService;
import com.pablosipac.kinalapp.service.IUsuarioService;
import com.pablosipac.kinalapp.service.IVentaService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vista/ventas")
public class VentasViewController {
    private IVentaService ventaService;
    private IClienteService clienteService;
    private IUsuarioService usuarioService;

    public VentasViewController(IVentaService ventaService, IClienteService clienteService, IUsuarioService usuarioService) {
        this.ventaService = ventaService;
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public String listar (Model model){
        model.addAttribute("ventas", ventaService.listarTodos());
        model.addAttribute("titulo", "Lista de Ventas");
        return "ventas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario (Model model){
        model.addAttribute("venta",  new Venta());
        model.addAttribute("clientes",  clienteService.listarTodos());
        model.addAttribute("usuarios",  usuarioService.listarTodos());
        return "ventas/formulario";
    }

    @GetMapping("/guardar")
    public String guardar (@ModelAttribute Venta venta, Model model){
        try {
            ventaService.guardar(venta);
            return "redirect:/vista/ventas";
        }catch (IllegalArgumentException e){
            model.addAttribute("error", e.getMessage());
            model.addAttribute("venta", venta);
            model.addAttribute("clientes", clienteService.listarTodos());
            model.addAttribute("usuarios",  usuarioService.listarTodos());
            return "ventas/formulario";
        }

    }
}
