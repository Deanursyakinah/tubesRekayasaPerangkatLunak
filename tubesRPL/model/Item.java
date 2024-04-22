package model;

import java.util.ArrayList;

public class Item {
    private int itemID;
    private String name;
    private String type;
    private String description;
    private double price;
    private int publisherID;
    private ArrayList<Review> reviews;
    private ItemStatus status;

    public Item() {

    }

    public Item(int itemID, String name, String type, String description, double price, int publisherID, ArrayList<Review> reviews) {
        this.itemID = itemID;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.publisherID = publisherID;
        this.reviews = reviews;
        this.status = ItemStatus.AVAILABLE;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getPublisherID() {
        return publisherID;
    }


    public void setPublisherID(int publisherID) {
        this.publisherID = publisherID;
    }

}
