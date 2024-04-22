package model;

import java.sql.Timestamp;

public class Transaction {
    private int transactionID;
    private int userID;
    private Timestamp date;
    private ShoppingCart shoppingCart;
    
    public Transaction(int transactionID, int userID, Timestamp date, ShoppingCart shoppingCart) {
        this.transactionID = transactionID;
        this.userID = userID;
        this.date = date;
        this.shoppingCart = shoppingCart;
    }
    public Transaction() {
    }
    public int getTransactionID() {
        return transactionID;
    }
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public Timestamp getDate() {
        return date;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
    
    
}
