package ru.geekbrain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrain.entities.Product;
import ru.geekbrain.entities.User;
import ru.geekbrain.repositories.ProductRepository;
import ru.geekbrain.repositories.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> filterByPrice (BigDecimal minPrice, BigDecimal maxPrice) {
//        if (minAge==0 && maxAge==0)
//            return repository.findAll();
//        if (minAge==0)
//            return repository.findByAgeGreaterThan(minAge);
//        if (maxAge==0)
//            return repository.findByAgeLessThan(maxAge);
        return productRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual(minPrice, maxPrice);
    }

    @Transactional
    public void save(Product product) {
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }
}
