package com.vbellos.dev.bookstore.models;

import java.util.ArrayList;
import java.util.List;

public class OrderEntry {
    private List<Book> books = new ArrayList<Book>();
    private List<Integer> copies = new ArrayList<Integer>();
    private List<Float> price = new ArrayList<Float>();
    private Float total;
    private String UserEmail;
    private String UserUID;

    public String getUserUID() {
        return UserUID;
    }

    public void setUserUID(String userUID) {
        this.UserUID = userUID;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        this.UserEmail = userEmail;
    }

    public void Inherit()
    {
        this.books = Order.getBooks();
        this.copies = Order.getCopies();
        this.price = Order.getPrice();
        this.total = Order.getTotal();
    }

    public String getText()
    {
        String text = "";

        for (int i= 0;i<getBooks().size();i++)
        {
            text = text +getCopies().get(i) + "x " + getBooks().get(i).getTitle() + "\n";
        }
        text = text + "Total: "+getTotal();

        return text;
    }



    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Integer> getCopies() {
        return copies;
    }

    public void setCopies(List<Integer> copies) {
        this.copies = copies;
    }

    public List<Float> getPrice() {
        return price;
    }

    public void setPrice(List<Float> price) {
        this.price = price;
    }

    public Float getTotal() {
        return this.total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
