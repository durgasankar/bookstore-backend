package com.bridgelabz.bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Book model which has the parameters which will hit with the database
 *
 * @author Durgasankar Mishra
 * @version 1.1
 * @created 2020-04-19
 */

@Entity
@Table(name = "user_books")
public class UserBookEntity {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;
    @Column(unique = true, nullable = false)
    private String bookCode;
    private String title;
    private String author;
    @Column(length = 10000)
    private String imageUrl;
    private String orderNumber;
    private int purchasedQuantity;
    private double price;
    @Column(length = 30)
    private String checkOutDateTime;
    @Column(length = 30)
    private String updateDateTime;
    private boolean isAddedToWatchlist;
    private boolean isAddedToCart;
    private boolean isCheckedOut;
    private long serialNumber;
    @JsonIgnore
    @ManyToMany(mappedBy = "booksList", fetch = FetchType.LAZY)
    private List<UserEntity> usersList;

    public UserBookEntity() { }

    public long getBookId() { return bookId; }

    public void setBookId( long bookId ) { this.bookId = bookId; }

    public long getSerialNumber() { return serialNumber; }

    public void setSerialNumber( long serialNumber ) { this.serialNumber = serialNumber; }

    public String getTitle() { return title; }

    public void setTitle( String title ) { this.title = title; }

    public String getAuthor() { return author; }

    public void setAuthor( String author ) { this.author = author; }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl( String imageUrl ) { this.imageUrl = imageUrl; }

    public String getCheckOutDateTime() { return checkOutDateTime; }

    public void setCheckOutDateTime( String checkOutDateTime ) { this.checkOutDateTime = checkOutDateTime; }

    public boolean isAddedToCart() { return isAddedToCart; }

    public void setAddedToCart( boolean addedToCart ) { isAddedToCart = addedToCart; }

    public boolean isCheckedOut() { return isCheckedOut; }

    public void setCheckedOut( boolean checkedOut ) { isCheckedOut = checkedOut; }


    public List<UserEntity> getUsersList() { return usersList; }

    public void setUsersList(UserEntity userEntity ) { this.usersList.add(userEntity); }

    public double getPrice() { return price; }

    public void setPrice( double price ) { this.price = price; }

    public String getUpdateDateTime() { return updateDateTime; }

    public void setUpdateDateTime( String updateDateTime ) { this.updateDateTime = updateDateTime; }

    public boolean isAddedToWatchlist() { return isAddedToWatchlist; }

    public void setAddedToWatchlist( boolean addedToWatchlist ) { isAddedToWatchlist = addedToWatchlist; }

    public int getPurchasedQuantity() { return purchasedQuantity; }

    public void setPurchasedQuantity( int purchasedQuantity ) { this.purchasedQuantity = purchasedQuantity; }

    public String getOrderNumber() { return orderNumber; }

    public void setOrderNumber( String orderNumber ) { this.orderNumber = orderNumber; }

    public String getBookCode() { return bookCode; }

    public void setBookCode( String bookCode ) { this.bookCode = bookCode; }


    @Override
    public String toString() {
        return "UserBookEntity{" +
                "bookId=" + bookId +
                ", bookCode='" + bookCode + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", purchasedQuantity=" + purchasedQuantity +
                ", price=" + price +
                ", checkOutDateTime='" + checkOutDateTime + '\'' +
                ", updateDateTime='" + updateDateTime + '\'' +
                ", isAddedToWatchlist=" + isAddedToWatchlist +
                ", isAddedToCart=" + isAddedToCart +
                ", isCheckedOut=" + isCheckedOut +
                ", serialNumber=" + serialNumber +
                ", usersList=" + usersList +
                '}';
    }
}
