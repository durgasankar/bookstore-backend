package com.bridgelabz.bookstore.dto;

public class BookDto {

    private String title;
    private String author;
    private String imageUrl;
    private double price;
    private int quantity;

    public BookDto() {
    }

    public String getTitle() { return title; }

    public void setTitle( String title ) { this.title = title; }

    public String getAuthor() { return author; }

    public void setAuthor( String author ) { this.author = author; }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl( String imageUrl ) { this.imageUrl = imageUrl; }

    public double getPrice() { return price; }

    public void setPrice( double price ) { this.price = price; }

    public int getQuantity() { return quantity; }

    public void setQuantity( int quantity ) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "BookDto{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
