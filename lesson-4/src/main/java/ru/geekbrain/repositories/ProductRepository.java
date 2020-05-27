package ru.geekbrain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrain.entities.Product;
import ru.geekbrain.entities.User;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPriceGreaterThanEqualAndPriceLessThanEqual(BigDecimal minPrice, BigDecimal maxPrice);

}
