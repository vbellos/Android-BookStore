package com.vbellos.dev.bookstore.models;

import com.vbellos.dev.bookstore.OrderChangedListener;

import java.util.ArrayList;
import java.util.List;


public class Order {



    static List<Book> books = new ArrayList<Book>();
    static List<Integer> copies = new ArrayList<Integer>();
    static List<Float> price = new ArrayList<Float>();

    public static List<Book> getBooks() {
        return books;
    }

    public static List<Integer> getCopies() {
        return copies;
    }

    public static List<Float> getPrice() {
        return price;
    }

    static private List<OrderChangedListener> listeners = new ArrayList<OrderChangedListener>();

    public static void addListener(OrderChangedListener toAdd) {
        listeners.add(toAdd);
    }

    public static  void Clear()
    {
        books = new ArrayList<Book>();
        copies = new ArrayList<Integer>();
        price = new ArrayList<Float>();
        orderchanged();
    }

    public static void orderchanged()
    {
        for (OrderChangedListener hl : listeners)
            hl.OrderChanged();
    }

    public static void add(Book book)
    {
        if(!books.contains(book) && Integer.parseInt(book.getCopies())>=1)
        {
            books.add(book);
            copies.add(1);
            price.add(Float.parseFloat(book.getPrice()));
        }
        else
        {
            int i = books.indexOf(book);
            if(Integer.parseInt(book.getCopies())>=copies.get(i)+1){}
            copies.set(i, copies.get(i)+1);
            price.set(i,copies.get(i)*Float.parseFloat(book.getPrice()));
        }
        orderchanged();
    }

    public static void updatequantity(Book book,int q)
    {
        if(books.contains(book))
        {
            int i = books.indexOf(book);
            if (q==0){remove(book);}
            else {
                copies.set(i, q);
                price.set(i, q * Float.parseFloat(book.getPrice()));
            }
        }
        orderchanged();
    }

    public static void remove(Book book)
    {
        if(books.contains(book))
        {
            int i = books.indexOf(book);
            books.remove(i);
            copies.remove(i);
            price.remove(i);
        }
        orderchanged();
    }

    public static Float getTotal()
    {
        float t = 0;
        for (Float f: price) {
            t=t+f;
        }
        return t;
    }


}
