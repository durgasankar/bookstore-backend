package com.bridgelabz.bookstore.models;

import javax.persistence.*;

@Entity
@Table(name = "admin_books")
public class AdminBookEntity {

    @Id
    @Column(name = "serial_number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long serialNumber;
    @Column(unique = true, nullable = false)
    private String bookCode;
    private String title;
    private String Author;
    private String imageUrl;
    private int availableQuantity;
    private double price;
    @Column(length = 30)
    private String additionDateTime;
    @Column(length = 30)
    private String updateDateTime;
    private boolean isRemoved;
    private boolean isOutOfStock;

    public AdminBookEntity() { }

    public long getBookId() { return serialNumber; }

    public void setBookId( long serialNumber ) { this.serialNumber = serialNumber; }

    public String getTitle() { return title; }

    public void setTitle( String title ) { this.title = title; }

    public String getAuthor() { return Author; }

    public void setAuthor( String author ) { Author = author; }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl( String imageUrl ) { this.imageUrl = imageUrl; }

    public int getAvailableQuantity() { return availableQuantity; }

    public void setAvailableQuantity( int availableQuantity ) { this.availableQuantity = availableQuantity; }

    public double getPrice() { return price; }

    public void setPrice( double price ) { this.price = price; }

    public String getAdditionDateTime() { return additionDateTime; }

    public void setAdditionDateTime( String additionDateTime ) { this.additionDateTime = additionDateTime; }

    public String getUpdateDateTime() { return updateDateTime; }

    public void setUpdateDateTime( String updateDateTime ) { this.updateDateTime = updateDateTime; }

    public boolean isRemoved() { return isRemoved; }

    public void setRemoved( boolean removed ) { isRemoved = removed; }

    public boolean isOutOfStock() { return isOutOfStock; }

    public void setOutOfStock( boolean outOfStock ) { isOutOfStock = outOfStock; }

    public String getBookCode() { return bookCode; }

    public void setBookCode( String bookCode ) { this.bookCode = bookCode; }

    @Override
    public String toString() {
        return "AdminBookEntity{" +
                "serialNumber=" + serialNumber +
                ", bookCode='" + bookCode + '\'' +
                ", title='" + title + '\'' +
                ", Author='" + Author + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", availableQuantity=" + availableQuantity +
                ", price=" + price +
                ", additionDateTime='" + additionDateTime + '\'' +
                ", updateDateTime='" + updateDateTime + '\'' +
                ", isRemoved=" + isRemoved +
                ", isOutOfStock=" + isOutOfStock +
                '}';
    }
}
