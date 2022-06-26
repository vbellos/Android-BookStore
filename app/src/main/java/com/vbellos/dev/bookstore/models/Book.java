package com.vbellos.dev.bookstore.models;

public class Book {

    private String title;
    private String description;
    private String image;
    private String price;
    private String copies;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Book(){};

    public Book(String uuid,String title, String description, String image, String price, String copies) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.price = price;
        this.copies = copies;
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCopies() {
        return copies;
    }

    public void setCopies(String copies) {
        this.copies = copies;
    }

    public boolean isAccepted()
    {
        if (!this.getCopies().isEmpty()&&!this.price.isEmpty() &&!this.getTitle().isEmpty()&&!this.getUuid().isEmpty())
        {
            int c = Integer.parseInt(this.getCopies());
            float p = Float.parseFloat(this.getPrice());
            if(c>0 && p>0){return true;}
        }
        return false;
    }
}
