package com.bridgelabz.bookstore.dto;

/**
 * Model class for book dto
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-14
 */
public class BookDto {

    private String title;
    private String author;
    private double price;
    private int availableQuantity;
    private String imageUrl;

    public BookDto() {
    }

    public String getTitle() { return title; }

    public void setTitle( String title ) { this.title = title; }

    public String getAuthor() { return author; }

    public void setAuthor( String author ) { this.author = author; }

    public double getPrice() { return price; }

    public void setPrice( double price ) { this.price = price; }

    public int getAvailableQuantity() { return availableQuantity; }

    public void setAvailableQuantity( int availableQuantity ) { this.availableQuantity = availableQuantity; }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl( String imageUrl ) { this.imageUrl = imageUrl; }

    @Override
    public String toString() {
        return "BookDto{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", availableQuantity=" + availableQuantity +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
