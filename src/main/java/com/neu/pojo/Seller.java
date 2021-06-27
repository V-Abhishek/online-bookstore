/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author abhishek
 */
public class Seller {

    private int sellerId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Book> catalog = new ArrayList<>(0);

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Book> getCatalog() {
        return catalog;
    }

    public void setCatalog(List<Book> catalog) {
        this.catalog = catalog;
    }

}
