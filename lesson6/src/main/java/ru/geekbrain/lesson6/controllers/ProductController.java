package ru.geekbrain.lesson6.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrain.lesson6.entities.Product;
import ru.geekbrain.lesson6.service.ProductService;


import java.math.BigDecimal;
import java.util.Optional;

@RequestMapping("/product")
@Controller
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    public String productList(Model model,
                              @RequestParam(name = "minPrice", required = false, defaultValue = "1.00") BigDecimal minPrice,
                              @RequestParam(name = "maxPrice", required = false, defaultValue = "100000000000.00") BigDecimal maxPrice,
                              @RequestParam(name = "partName", required = false, defaultValue = "") String partName,
                              @RequestParam(name = "page") Optional<Integer> page,
                              @RequestParam(name = "size") Optional<Integer> size) {
        logger.info("Product list");
        Page<Product> productsPage;
        if (!partName.equals("")){
            System.out.println("partName!=null");
            productsPage = productService.filterByPriceAndName(minPrice, maxPrice, partName,
                    PageRequest.of(page.orElse(1) - 1, size.orElse(5)));

        } else{
            System.out.println("partName==null");
            productsPage = productService.filterByPrice(minPrice, maxPrice,
                    PageRequest.of(page.orElse(1) - 1, size.orElse(5)));
        }

        model.addAttribute("productsPage", productsPage);
        model.addAttribute("prevPageNumber", productsPage.hasPrevious() ? productsPage.previousPageable().getPageNumber() + 1 : -1);
        model.addAttribute("nextPageNumber", productsPage.hasNext() ? productsPage.nextPageable().getPageNumber() + 1 : -1);

        return "products";
    }

    @GetMapping("new")
    public String createProduct(Model model) {
        logger.info("Create product form");

        model.addAttribute("product", new Product());
        return "product";
    }

    @PostMapping
    public String saveProduct(Product product) {
        logger.info("Save product method");

        productService.save(product);
        return "redirect:/product";
    }

    @GetMapping("edit")
    public String editProduct(Model model) {
        logger.info("Edit Product form");

        model.addAttribute("product", new Product());
        return "productEdit";
    }

    @PostMapping ("productEdit")
    public String editProduct(Product product, BindingResult bindingResult) {
        logger.info("Edit user method");

        if (bindingResult.hasErrors()) {
            return "product";
        }


        productService.editProduct(product);
        return "redirect:/product";
    }
}
