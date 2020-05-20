package ru.geekbrain.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrain.entities.Product;
import ru.geekbrain.entities.User;
import ru.geekbrain.repositories.ProductRepository;
import ru.geekbrain.repositories.UserRepository;

@RequestMapping("/product")
@Controller
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ru.geekbrain.controllers.ProductController.class);

    private ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String productList(Model model) {
        logger.info("Product list");

        model.addAttribute("products", productRepository.findAll());
        return "products";
    }

    @GetMapping("new")
    public String createProduct(Model model) {
        logger.info("Create product form");

        model.addAttribute("product", new Product());
        return "product";
    }

    @PostMapping
    public String saveUser(Product product) {
        logger.info("Save product method");

        productRepository.save(product);
        return "redirect:/product";
    }
}
