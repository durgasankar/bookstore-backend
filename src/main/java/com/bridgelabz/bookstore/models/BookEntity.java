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
    private String orderNumber;
    private int availableQuantity;
    private int purchasedQuantity;
    private double price;
    @Column(length = 30)
    private String additionDateTime;
    @Column(length = 30)
    private String checkOutDateTime;
    @Column(length = 30)
    private String updateDateTime;
    private boolean isRemoved;
    private boolean isAddedToWatchlist;
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

    public void setUsersList(UserEntity userEntity ) { this.usersList.add(userEntity); }

    public double getPrice() { return price; }

    public void setPrice( double price ) { this.price = price; }

    public boolean isRemoved() { return isRemoved; }

    public void setRemoved( boolean removed ) { isRemoved = removed; }

    public String getUpdateDateTime() { return updateDateTime; }

    public void setUpdateDateTime( String updateDateTime ) { this.updateDateTime = updateDateTime; }

    public boolean isAddedToWatchlist() { return isAddedToWatchlist; }

    public void setAddedToWatchlist( boolean addedToWatchlist ) { isAddedToWatchlist = addedToWatchlist; }

    public int getAvailableQuantity() { return availableQuantity; }

    public void setAvailableQuantity( int availableQuantity ) { this.availableQuantity = availableQuantity; }

    public int getPurchasedQuantity() { return purchasedQuantity; }

    public void setPurchasedQuantity( int purchasedQuantity ) { this.purchasedQuantity = purchasedQuantity; }

    public String getOrderNumber() { return orderNumber; }

    public void setOrderNumber( String orderNumber ) { this.orderNumber = orderNumber; }

    @Override
    public String toString() {
        return "BookEntity{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", Author='" + Author + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", availableQuantity=" + availableQuantity +
                ", purchasedQuantity=" + purchasedQuantity +
                ", price=" + price +
                ", additionDateTime='" + additionDateTime + '\'' +
                ", checkOutDateTime='" + checkOutDateTime + '\'' +
                ", updateDateTime='" + updateDateTime + '\'' +
                ", isRemoved=" + isRemoved +
                ", isAddedToWatchlist=" + isAddedToWatchlist +
                ", isAddedToCart=" + isAddedToCart +
                ", isCheckedOut=" + isCheckedOut +
                ", isOutOfStock=" + isOutOfStock +
                ", usersList=" + usersList +
                '}';
    }
}
