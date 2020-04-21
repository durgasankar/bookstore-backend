//package com.bridgelabz.bookstore.models;
//
//import javax.persistence.*;
//import java.util.Arrays;
//
///**
// * Book image model which has the parameters which will hit with the database
// *
// * @author Durgasankar Mishra
// * @version 1.1
// * @created 2020-04-20
// */
//@Embeddable
//public class BookImage {
//    private String name;
//    private String type;
//    @Column(length = 1000)
//    private byte[] picByte;
//
//    public BookImage( String name, String type, byte[] picByte ) {
//        this.name = name;
//        this.type = type;
//        this.picByte = picByte;
//    }
//
//    public String getName() { return name; }
//
//    public void setName( String name ) { this.name = name; }
//
//    public String getType() { return type; }
//
//    public void setType( String type ) { this.type = type; }
//
//    public byte[] getPicByte() { return picByte; }
//
//    public void setPicByte( byte[] picByte ) { this.picByte = picByte; }
//
//    @Override
//    public String toString() {
//        return "BookImage{" +
//                ", name='" + name + '\'' +
//                ", type='" + type + '\'' +
//                ", picByte=" + Arrays.toString (picByte) +
//                '}';
//    }
//}
