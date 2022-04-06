package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import java.util.List;
import java.util.stream.Collectors;

public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        var products = basket.getProducts();
        var productsMap = products.stream().collect(Collectors.toMap(product -> product, product -> 1, Integer::sum));
        List<ReceiptEntry> receiptEntries = productsMap.entrySet().stream().map(entry -> new ReceiptEntry(entry.getKey(), entry.getValue())).collect(Collectors.toList());

        return new Receipt(receiptEntries);
    }
}
