package com.virtuslab.internship.controller;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ProductController {

    private final ProductDb productDb = new ProductDb();

    @GetMapping("/products")
    public Set<Product> showProducts() {
        return productDb.getProducts();
    }
}
