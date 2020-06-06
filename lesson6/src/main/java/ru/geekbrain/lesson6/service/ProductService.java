package ru.geekbrain.lesson6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrain.lesson6.entities.Product;
import ru.geekbrain.lesson6.repositories.ProductRepository;


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

    @Transactional
    public Page<Product> filterByPrice (BigDecimal minPrice, BigDecimal maxPrice, Pageable pabeable) {
//        if (minAge==0 && maxAge==0)
//            return repository.findAll();
//        if (minAge==0)
//            return repository.findByAgeGreaterThan(minAge);
//        if (maxAge==0)
//            return repository.findByAgeLessThan(maxAge);
        return productRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual(minPrice, maxPrice, pabeable);
    }

    @Transactional
    public void editProduct(Product product){
        Optional<Product> productFromDB = productRepository.findByTitle(product.getTitle());
        if(productFromDB.isPresent()){
            Product p = productFromDB.get();
            p.setDescription(product.getDescription());
            p.setPrice(product.getPrice());
            productRepository.save(p);
        }
    }

    @Transactional
    public void deleteProduct(long id){
        productRepository.deleteById(id);
    }


    @Transactional
    public Page<Product> filterByPriceAndName (BigDecimal minPrice, BigDecimal maxPrice, String partName, Pageable pageable) {
        String pName = partName+"%";
        return productRepository.findByTitleLikeAndPriceBetween(pName, minPrice, maxPrice, pageable);
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
