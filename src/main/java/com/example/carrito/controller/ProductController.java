package com.example.carrito.controller;

import com.example.carrito.entity.Product;
import com.example.carrito.service.ProductService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// ProductController
//@RestController
@Controller
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    //form input
    @GetMapping("/product_form")
    public String mostrarFormulario(Model model) {
        //List<Product> products = productService.listar();
        model.addAttribute("product", new Product());
        return "products_input";
    }
    
    @PostMapping("/agregar")
    public String agregarProducto(@ModelAttribute("product") Product product) {
        productService.save(product);
        return "redirect:/api/products/listar";
    }

    //listar products @GetMapping("/listar")
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Product> products = productService.listar();
        model.addAttribute("products", products);
        return "products";
    }
    
    @PostMapping("/{id}/edit")
    public String actualizarProducto(@PathVariable("id") Long id, @ModelAttribute("producto") Product productoActualizado) {
        Product productoExistente = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));
        
        productoExistente.setName(productoActualizado.getName());
        productoExistente.setDescription(productoActualizado.getDescription());
        productoExistente.setPrice(productoActualizado.getPrice());
        
        productService.save(productoExistente);
        
        return "redirect:/api/products/listar";
    }
    
    @GetMapping("/{id}/edit")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));
        model.addAttribute("product", product);
        return "edit_form";
    }
    
    @GetMapping("/{id}/delete")
    public String eliminarProducto(@PathVariable("id") Long id) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));
        productService.deleteById(id);
        return "redirect:/api/products/listar";
    }

    //Create a new product
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    //Read an product
    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable(value = "id") Long productId) {
        Optional<Product> oProduct = productService.findById(productId);
        if (!oProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oProduct);
    }

    //Update an product
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Product productDetails, @PathVariable(value = "id") Long productId) {
        Optional<Product> product = productService.findById(productId);
        if (!product.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product.get()));
    }

    //Delete an product
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long productId) {
        if (!productService.findById(productId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteById(productId);
        return ResponseEntity.ok().build();
    }

    //Read all products
    @GetMapping
    @ResponseBody
    public List<Product> readAll() {
        List<Product> products = StreamSupport
                .stream(productService.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return products;
    }
}
