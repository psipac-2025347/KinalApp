package com.pablosipac.kinalapp.controller;

import com.pablosipac.kinalapp.entity.DetalleVenta;
import com.pablosipac.kinalapp.service.IDetalleVentaService;
import com.pablosipac.kinalapp.service.IProductoService;
import com.pablosipac.kinalapp.service.IVentaService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vista/detalles")
public class DetalleVentaViewController {

    private final IDetalleVentaService detalleVentaService;
    private final IProductoService productoService;
    private final IVentaService ventaService;

    public DetalleVentaViewController(IDetalleVentaService detalleVentaService, IProductoService productoService, IVentaService ventaService) {
        this.detalleVentaService = detalleVentaService;
        this.productoService = productoService;
        this.ventaService = ventaService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public String listar(Model model) {
        model.addAttribute("detalles", detalleVentaService.listarTodos());
        model.addAttribute("titulo", "Lista de Detalles de Venta");
        return "detalles/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("detalle", new DetalleVenta());
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("ventas", ventaService.listarTodos());
        model.addAttribute("titulo", "Nuevo Detalle de Venta");
        return "detalles/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute DetalleVenta detalle, Model model) {
        try {
            detalleVentaService.guardar(detalle);
            return "redirect:/vista/detalles";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("detalle", detalle);
            model.addAttribute("productos", productoService.listarTodos());
            model.addAttribute("ventas", ventaService.listarTodos());
            return "detalles/formulario";
        }
    }
}
