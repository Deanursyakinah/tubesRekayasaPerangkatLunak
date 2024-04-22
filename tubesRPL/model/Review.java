package model;

public class Review {
    private int reviewID;
    private String reviewText;
    private Item item;
    private User user;
    
    public Review(int reviewID, String reviewText, Item item, User user) {
        this.reviewID = reviewID;
        this.reviewText = reviewText;
        this.item = item;
        this.user = user;
    }

    public Review() {
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

