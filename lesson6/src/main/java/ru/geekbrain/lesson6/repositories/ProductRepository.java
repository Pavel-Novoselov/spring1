package ru.geekbrain.lesson6.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrain.lesson6.entities.Product;


import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByPriceGreaterThanEqualAndPriceLessThanEqual(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

 //   @Query("from Product p where p.title like :name and p.price > :minPrice and p.price < :maxPrice")
    Page<Product>  findByTitleLikeAndPriceBetween (String title, BigDecimal minPrice, BigDecimal maxPrice, Pageable pabeable);

    Optional<Product> findByTitle(String title);
}
