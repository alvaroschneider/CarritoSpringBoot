package com.example.carrito.service;

import com.example.carrito.entity.Sale;
import com.example.carrito.repository.SaleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Override
    public List<Sale> listar() {
        return (List<Sale>)saleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Sale> findAll() {
        return saleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Sale> findAll(Pageable pageable) {
        return saleRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Sale> findById(Long id) {
        return saleRepository.findById(id);
    }

    @Override
    @Transactional
    public Sale save(Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        saleRepository.deleteById(id);
    }

}
