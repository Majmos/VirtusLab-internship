package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountTest {

    private final DiscountApplier discountApplier = new DiscountApplier();

    @Test
    void shouldApply10PercentDiscountWhenPriceIsAbove50() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        var steak = productDb.getProduct("Steak");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 1));
        receiptEntries.add(new ReceiptEntry(steak, 1));

        var receipt = new Receipt();
        receipt.update(receiptEntries);
        var expectedTotalPrice = cheese.price().add(steak.price()).multiply(BigDecimal.valueOf(0.9));

        // When
        discountApplier.accept(receipt);

        // Then
        assertEquals(expectedTotalPrice, receipt.getTotalPrice());
        assertEquals(1, receipt.getDiscounts().size());
    }

    @Test
    void shouldNotApply10PercentDiscountWhenPriceIsBelow50() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 2));

        var receipt = new Receipt();
        receipt.update(receiptEntries);
        var expectedTotalPrice = cheese.price().multiply(BigDecimal.valueOf(2));

        // When
        discountApplier.accept(receipt);

        // Then
        assertEquals(expectedTotalPrice, receipt.getTotalPrice());
        assertEquals(0, receipt.getDiscounts().size());
    }

    @Test
    void shouldApply15PercentDiscountWhenThereAreAtLeast3GrainsInTheBasket() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 2));

        var receipt = new Receipt();
        receipt.update(receiptEntries);
        var expectedTotalPrice = bread.price().add(cereals.price().multiply(BigDecimal.valueOf(2)))
                .multiply(BigDecimal.valueOf(0.85));

        // When
        discountApplier.accept(receipt);

        // Then
        assertEquals(expectedTotalPrice, receipt.getTotalPrice());
        assertEquals(1, receipt.getDiscounts().size());
    }

    @Test
    void shouldNotApply15PercentDiscountWhenThereAreLessThan3GrainsInTheBasket() {
        // Given
        var productDb = new ProductDb();
        var milk = productDb.getProduct("Milk");
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(milk, 1));
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 1));

        var receipt = new Receipt();
        receipt.update(receiptEntries);
        var expectedTotalPrice = milk.price().add(bread.price()).add(cereals.price());

        // When
        discountApplier.accept(receipt);

        // Then
        assertEquals(expectedTotalPrice, receipt.getTotalPrice());
        assertEquals(0, receipt.getDiscounts().size());
    }

    @Test
    void shouldApply15PercentDiscountAndNotApply10PercentDiscountWhenNewPriceIsBelow50() {
        // Given
        var productDb = new ProductDb();
        var pork = productDb.getProduct("Pork");
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(pork, 2));
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 2));

        var receipt = new Receipt();
        receipt.update(receiptEntries);
        var expectedTotalPrice = pork.price().multiply(BigDecimal.valueOf(2)).add(bread.price())
                .add(cereals.price().multiply(BigDecimal.valueOf(2)))
                .multiply(BigDecimal.valueOf(0.85));

        // When
        discountApplier.accept(receipt);

        // Then
        assertEquals(expectedTotalPrice, receipt.getTotalPrice());
        assertEquals(1, receipt.getDiscounts().size());
    }

    @Test
    void shouldApply15PercentDiscountAndThenApply10PercentDiscountWhenNewPriceIsAbove50() {
        // Given
        var productDb = new ProductDb();
        var steak = productDb.getProduct("Steak");
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(steak, 2));
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 2));

        var receipt = new Receipt();
        receipt.update(receiptEntries);
        var expectedTotalPrice = steak.price().multiply(BigDecimal.valueOf(2)).add(bread.price())
                .add(cereals.price().multiply(BigDecimal.valueOf(2)))
                .multiply(BigDecimal.valueOf(0.85)).multiply(BigDecimal.valueOf(0.9));

        // When
        discountApplier.accept(receipt);

        // Then
        assertEquals(expectedTotalPrice, receipt.getTotalPrice());
        assertEquals(2, receipt.getDiscounts().size());
    }
}
