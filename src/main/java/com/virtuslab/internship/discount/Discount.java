package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

import java.math.BigDecimal;

public enum Discount {
    FIFTEEN_PERCENT{
        @Override
        void applyDiscount(Receipt receipt) {
            receipt.setTotalPrice(receipt.getTotalPrice().multiply(BigDecimal.valueOf(0.85)));
            receipt.getDiscounts().add(Discount.FIFTEEN_PERCENT);
        }
    },
    TEN_PERCENT{
        @Override
        void applyDiscount(Receipt receipt) {
            receipt.setTotalPrice(receipt.getTotalPrice().multiply(BigDecimal.valueOf(0.9)));
            receipt.getDiscounts().add(Discount.TEN_PERCENT);
        }
    };

    abstract void applyDiscount(Receipt receipt);
}

