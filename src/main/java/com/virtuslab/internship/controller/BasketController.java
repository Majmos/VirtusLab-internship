package com.virtuslab.internship.controller;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.basket.BasketDTO;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasketController {

    private final ReceiptGenerator receiptGenerator = new ReceiptGenerator();
    private final ProductDb productDb = new ProductDb();

    @PostMapping("/basket")
    public Receipt checkout(@RequestBody BasketDTO basketDTO) {
        return receiptGenerator.generate(mapBasket(basketDTO));
    }

    private Basket mapBasket(BasketDTO basketDTO) {
        Basket basket = new Basket();
        for (var productName : basketDTO.getProductNames()) {
            var product = productDb.getProduct(productName);
            if (product != null) {
                basket.addProduct(product);
            }
        }
        return basket;
    }
}
