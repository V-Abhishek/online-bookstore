/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.dao;

import com.neu.pojo.Seller;
import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author abhishek
 */
public class SellerDAO {

    public boolean isUnique(String email) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        boolean exist = false;
        try {
            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createQuery("FROM Seller WHERE email = :email");
            query.setString("email", email);
            ArrayList<Seller> sellerList = (ArrayList<Seller>) query.list();
            connectionDAO.commit();
            if (!sellerList.isEmpty()) {
                exist = true;
            }
        } catch (HibernateException e) {
            connectionDAO.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            connectionDAO.close();
        }
        return exist;
    }

    public int addNewSeller(String firstName, String lastName, String email, String password) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        int result = 0;
        try {
            connectionDAO.beginTransaction();
            Seller seller = new Seller();
            seller.setFirstName(firstName);
            seller.setLastName(lastName);
            seller.setEmail(email);
            seller.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            connectionDAO.getSession().save(seller);
            connectionDAO.commit();
            result = 1;
        } catch (HibernateException e) {
            connectionDAO.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            connectionDAO.close();
        }
        return result;
    }

    public Seller authenticateSeller(String email, String password) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Seller seller = null;
        Boolean valid = false;
        try {
            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createQuery("FROM Seller WHERE email = :email");
            query.setString("email", email);
            seller = (Seller) query.uniqueResult();
            connectionDAO.commit();
            if (seller != null) {
                valid = checkPass(password, seller.getPassword());
                if (!valid) {
                    seller = null;
                }
            }
        } catch (HibernateException e) {
            connectionDAO.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            connectionDAO.close();
        }
        return seller;
    }

    private boolean checkPass(String plainPassword, String hashedPassword) {
        boolean valid = false;
        if (BCrypt.checkpw(plainPassword, hashedPassword)) {
            valid = true;
        }
        return valid;
    }

    public int updateSellerProfile(Seller seller, String firstName, String lastName) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        int id = seller.getSellerId();
        int result = 0;
        try {
            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createQuery("FROM Seller WHERE sellerId = :id");
            query.setInteger("id", id);
            seller = (Seller) query.uniqueResult();
            if (seller != null) {
                seller.setFirstName(firstName);
                seller.setLastName(lastName);
                connectionDAO.getSession().update(seller);
                connectionDAO.commit();
                result = 1;
            }
        } catch (HibernateException e) {
            connectionDAO.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            connectionDAO.close();
        }
        return result;
    }

    public int updateSellerPassword(Seller seller, String password) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        int id = seller.getSellerId();
        int result = 0;
        try {
            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createQuery("FROM Seller WHERE sellerId = :id");
            query.setInteger("id", id);
            seller = (Seller) query.uniqueResult();
            if (seller != null) {
                seller.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
                connectionDAO.getSession().update(seller);
                connectionDAO.commit();
                result = 1;
            }
        } catch (HibernateException e) {
            connectionDAO.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            connectionDAO.close();
        }
        return result;
    }

    public Seller getSeller(int sellerId) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Seller seller = null;
        try {
            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createQuery("FROM Seller WHERE sellerId = :sellerId");
            query.setInteger("sellerId", sellerId);
            seller = (Seller) query.uniqueResult();
            connectionDAO.commit();
        } catch (HibernateException e) {
            connectionDAO.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            connectionDAO.close();
        }
        return seller;
    }

}
