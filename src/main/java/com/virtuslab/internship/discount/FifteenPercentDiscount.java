package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;

import java.math.BigDecimal;
import java.util.Objects;

public class FifteenPercentDiscount {
    public static String NAME = "FifteenPercentDiscount";

    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.85));
            var discounts = receipt.discounts();
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    private boolean shouldApply(Receipt receipt) {
        return receipt.entries().stream().filter(Objects::nonNull)
                .filter(receiptEntry -> receiptEntry.product().type().equals(Product.Type.GRAINS))
                .reduce(0, ((integer, entry) -> integer + entry.quantity()), Integer::sum) >= 3;
    }
}
