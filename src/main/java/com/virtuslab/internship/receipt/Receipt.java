package com.virtuslab.internship.receipt;

import com.virtuslab.internship.discount.Discount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private List<ReceiptEntry> entries = new ArrayList<>();
    private List<Discount> discounts = new ArrayList<>();
    private BigDecimal totalPrice = new BigDecimal(0);

    public void update(List<ReceiptEntry> entries) {
        this.setEntries(entries);
        this.setTotalPrice(entries.stream()
                .map(ReceiptEntry::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public List<ReceiptEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<ReceiptEntry> entries) {
        this.entries = entries;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
