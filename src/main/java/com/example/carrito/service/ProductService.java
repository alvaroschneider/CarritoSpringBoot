package com.example.carrito.service;

import com.example.carrito.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    public List<Product> listar();
    public Iterable<Product> findAll();
    public Page<Product> findAll(Pageable pageable);
    public Optional<Product> findById(Long id);
    public Product save(Product user);
    public void deleteById(Long id);
}
