package com.example.carrito.controller;

import com.example.carrito.entity.Sale;
import com.example.carrito.service.SaleService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@RestController
@Controller
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    //listar sales @GetMapping("/listar")
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Sale> sales = saleService.listar();
        model.addAttribute("sales", sales);
        return "sales";
    }

    //Create a new sale
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Sale sale) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.save(sale));
    }

    //Read an sale
    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable(value = "id") Long saleId) {
        Optional<Sale> oSale = saleService.findById(saleId);
        if (!oSale.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oSale);
    }

    //Update an sale
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Sale saleDetails, @PathVariable(value = "id") Long saleId) {
        Optional<Sale> sale = saleService.findById(saleId);
        if (!sale.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.save(sale.get()));
    }

    //Delete an sale
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long saleId) {
        if (!saleService.findById(saleId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        saleService.deleteById(saleId);
        return ResponseEntity.ok().build();
    }
    
    //Read all sales
    @GetMapping
    @ResponseBody
    public List<Sale> readAll(){
        List<Sale> sales = StreamSupport
                .stream(saleService.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return sales;
    }
}
