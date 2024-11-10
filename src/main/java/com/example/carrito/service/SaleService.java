package com.example.carrito.service;

import com.example.carrito.entity.Sale;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SaleService {
    public List<Sale> listar();
    public Iterable<Sale> findAll();
    public Page<Sale> findAll(Pageable pageable);
    public Optional<Sale> findById(Long id);
    public Sale save(Sale user);
    public void deleteById(Long id);
}
