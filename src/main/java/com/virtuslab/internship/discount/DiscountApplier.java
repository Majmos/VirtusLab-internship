package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class DiscountApplier implements Consumer<Receipt> {

    @Override
    public void accept(Receipt receipt) {
        for (Discount discount: Discount.values()) {
            if (shouldApply(receipt, discount)) {
                discount.applyDiscount(receipt);
            }
        }
    }

    private boolean shouldApply(Receipt receipt, Discount discount) {
        return switch (discount) {
            case FIFTEEN_PERCENT -> receipt.getEntries().stream().filter(Objects::nonNull)
                    .filter(receiptEntry -> receiptEntry.product().type().equals(Product.Type.GRAINS))
                    .reduce(0, ((integer, entry) -> integer + entry.quantity()), Integer::sum) >= 3;
            case TEN_PERCENT -> receipt.getTotalPrice().compareTo(BigDecimal.valueOf(50)) > 0;
        };
    }
}
