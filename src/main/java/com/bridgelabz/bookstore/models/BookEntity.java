package com.bridgelabz.bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;
    private String title;
    private String Author;
    private String imageUrl;
    private int quantity;
    private double price;
    @Column(length = 30)
    private String additionDateTime;
    @Column(length = 30)
    private String checkOutDateTime;
    private boolean isAddedToCart;
    private boolean isCheckedOut;
    private boolean isOutOfStock;
    @JsonIgnore
    @ManyToMany(mappedBy = "booksList", fetch = FetchType.LAZY)
    private List<UserEntity> usersList;

    public BookEntity() { }

    public long getBookId() { return bookId; }

    public void setBookId( long bookId ) { this.bookId = bookId; }

    public String getTitle() { return title; }

    public void setTitle( String title ) { this.title = title; }

    public String getAuthor() { return Author; }

    public void setAuthor( String author ) { Author = author; }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl( String imageUrl ) { this.imageUrl = imageUrl; }

    public int getQuantity() { return quantity; }

    public void setQuantity( int quantity ) { this.quantity = quantity; }

    public String getAdditionDateTime() { return additionDateTime; }

    public void setAdditionDateTime( String additionDateTime ) { this.additionDateTime = additionDateTime; }

    public String getCheckOutDateTime() { return checkOutDateTime; }

    public void setCheckOutDateTime( String checkOutDateTime ) { this.checkOutDateTime = checkOutDateTime; }

    public boolean isAddedToCart() { return isAddedToCart; }

    public void setAddedToCart( boolean addedToCart ) { isAddedToCart = addedToCart; }

    public boolean isCheckedOut() { return isCheckedOut; }

    public void setCheckedOut( boolean checkedOut ) { isCheckedOut = checkedOut; }

    public boolean isOutOfStock() { return isOutOfStock; }

    public void setOutOfStock( boolean outOfStock ) { isOutOfStock = outOfStock; }

    public List<UserEntity> getUsersList() { return usersList; }

    public void setUsersList( List<UserEntity> usersList ) { this.usersList = usersList; }

    public double getPrice() { return price; }

    public void setPrice( double price ) { this.price = price; }

    @Override
    public String toString() {
        return "BookEntity{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", Author='" + Author + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", additionDateTime='" + additionDateTime + '\'' +
                ", checkOutDateTime='" + checkOutDateTime + '\'' +
                ", isAddedToCart=" + isAddedToCart +
                ", isCheckedOut=" + isCheckedOut +
                ", isOutOfStock=" + isOutOfStock +
                ", usersList=" + usersList +
                '}';
    }
}
