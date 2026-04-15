package com.pablosipac.kinalapp.controller;

import com.pablosipac.kinalapp.entity.Producto;
import com.pablosipac.kinalapp.service.IProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vista/productos")
public class ProductoViewController {
    private final IProductoService productoService;

    public ProductoViewController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public String listar(Model model) {
        // Model es una variable donde pones datos para que la plantilla HTML los use
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("titulo", "Lista de Productos");
        return "productos/lista"; // ← busca el archivo lista.html
    }

    @GetMapping("/nuevo")
    public  String mostrarFormularioNuevo(Model model){
        model.addAttribute("producto", new Producto());
        model.addAttribute("titulo", "Nuevo Producto");
        return "productos/formulario";
    }

    @PostMapping("/guardar")
    public  String guardar(@ModelAttribute Producto producto, Model model) {
        //@ModelAttribute toma los campos  del formulario HTML y los convierte al objeto cliente automaticamente
        try {
            productoService.guardar(producto);// redirige a la lista
            return "redirect:/vista/productos";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("producto", producto);
            return "productos/formulario";

        }
    }
        @GetMapping("/editar/{codigoProducto}")
        public String mostrarFormularioEditarPC(@PathVariable Long codigoProducto, Model model){
            return productoService.buscarPorcodigoProducto(codigoProducto)
                    .map(producto -> {
                        model.addAttribute("producto", codigoProducto);
                        model.addAttribute("Titulo", "Editar Producto" );
                        return "productos/formulario"; // es el mismo formulario
                    })
                    .orElse("redirect:/vista/productos");
        }

        @GetMapping("/eliminar/{codigoProducto}")
        public String eliminar (@PathVariable Long codigoProducto){
        if(productoService.existePorcodigoProducto(codigoProducto)){
            productoService.eliminar(codigoProducto);
        }
        return "redirect:/vista/producto";
        }

}


