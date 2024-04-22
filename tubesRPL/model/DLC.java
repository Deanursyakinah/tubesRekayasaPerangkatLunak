package model;

import java.util.ArrayList;

public class DLC extends Item{
    public DLC(int itemID, String name, String type, String description, double price, int publisherID, ArrayList<Review> reviews) {
        super(itemID, name, "DLC", description, price, publisherID, reviews);
    }

    public DLC() {
    }

}
