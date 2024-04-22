package model;

import java.util.ArrayList;

public class ShoppingCart {
    private int transactionID;
    private int itemID;
    private String description;

    

    public ShoppingCart(int transactionID, int itemID, String description) {
        this.transactionID = transactionID;
        this.itemID = itemID;
        this.description = description;
    }

    
    public ShoppingCart() {
    }

    public int getTransactionID() {
        return transactionID;
    }


    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }


    public int getitemID() {
        return itemID;
    }


    public void setitemID(int itemID) {
        this.itemID = itemID;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }
}
