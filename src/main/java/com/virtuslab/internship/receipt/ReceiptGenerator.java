package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.DiscountApplier;

import java.util.List;
import java.util.stream.Collectors;

public class ReceiptGenerator {

    private final DiscountApplier discountApplier = new DiscountApplier();

    public Receipt generate(Basket basket) {
        var products = basket.getProducts();
        var productsMap = products.stream().collect(Collectors
                .toMap(product -> product, product -> 1, Integer::sum));
        List<ReceiptEntry> receiptEntries = productsMap.entrySet().stream()
                .map(entry -> new ReceiptEntry(entry.getKey(), entry.getValue())).collect(Collectors.toList());

        Receipt receipt = new Receipt();
        receipt.update(receiptEntries);
        discountApplier.accept(receipt);
        return receipt;
    }
}
