package ru.geekbrain.lesson6.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrain.lesson6.entities.Product;
import ru.geekbrain.lesson6.entities.User;
import ru.geekbrain.lesson6.service.ProductService;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api/product")
@RestController
public class ProductRestController {

    private final ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<Product> findAll(){
        return productService.findAll();
    }

    @GetMapping("/{id}/id")
    public Product findById(@PathVariable("id") long id){
        return productService.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public void createProduct(@RequestBody Product product){
        if (product.getId() !=null){
            throw new IllegalArgumentException("Id found! Bad request!");
        }
        productService.save(product);
    }

    @PutMapping
    public void updateProduct(@RequestBody Product product){
        productService.save(product);
    }

    @DeleteMapping("/{id}/id")
    public void deleteProduct(@PathVariable("id") long id){
        productService.deleteProduct(id);
    }

    public ResponseEntity<String> notFoundExeptionHandler(NotFoundException notFoundException){
        return new ResponseEntity<>("notFound", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> illegalArgumentExceptionHandler(IllegalArgumentException illegalArgumentException){
        return new ResponseEntity<>(illegalArgumentException.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

}
