package ru.geekbrain.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.geekbrain.entities.Product;
import ru.geekbrain.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {

    private final AtomicLong identityGen;

    List<Product> products;

    public ProductRepository() {
        this.identityGen = new AtomicLong(0);
        this.products = new ArrayList<>();
    }

    public List<Product> findAll() {
        return products;
    }

    public void save(Product product) {

        long id = identityGen.incrementAndGet();
        product.setId(id);
        products.add(product);
    }

    public Product findById(long id) {
        return products.get((int)id);
    }
}
