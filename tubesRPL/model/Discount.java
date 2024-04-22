package model;

import java.time.LocalDate;

public class Discount {
    private int discountID;
    private double amount;
    private LocalDate startDate;
    private LocalDate endDate;

    public Discount(int discountID, double amount, LocalDate startDate, LocalDate endDate) {
        this.discountID = discountID;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getDiscountID() {
        return discountID;
    }

    public void setDiscountID(int discountID) {
        this.discountID = discountID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
