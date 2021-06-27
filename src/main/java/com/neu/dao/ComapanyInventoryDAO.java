/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.dao;

import com.neu.pojo.Book;
import com.neu.pojo.CartBook;
import com.neu.pojo.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 *
 * @author abhishek
 */
public class ComapanyInventoryDAO {

    public int placeToMyCart(ArrayList<CartBook> requestList) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        int result = 0;
        try {
            if (requestList != null) {
                for (CartBook cartBook : requestList) {
                    connectionDAO.beginTransaction();
                    connectionDAO.getSession().save(cartBook);
                    connectionDAO.commit();
                    result = 1;
                }
            }
        } catch (HibernateException e) {
            connectionDAO.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            connectionDAO.close();
        }
        return result;
    }

    public int placeToMyCart(CartBook cartBook) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        int result = 0;
        try {
            if (cartBook != null) {
                connectionDAO.beginTransaction();
                connectionDAO.getSession().save(cartBook);
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

    public ArrayList<Book> getBooks(User user) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        ArrayList<Book> bookList = new ArrayList<Book>();
        try {
            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createQuery("FROM Book");
            ArrayList<Book> tempList = (ArrayList<Book>) query.list();
            connectionDAO.commit();
            for (Book book : tempList) {
                if (!book.getSeller().getEmail().equalsIgnoreCase(user.getEmail())) {
                    bookList.add(book);
                }
            }
            Collections.sort(bookList, new Comparator<Book>() {
                public int compare(Book b1, Book b2) {
                    int q1 = b1.getQuantity();
                    int q2 = b2.getQuantity();
                    return q1 - q2;
                }

            });
        } catch (HibernateException e) {
            connectionDAO.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            connectionDAO.close();
        }
        return bookList;
    }

    public ArrayList<CartBook> getUnOrderedBooks(User user) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        ArrayList<CartBook> bookList = null;
        try {
            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createQuery("FROM CartBook WHERE userId= :id");
            query.setInteger("id", user.getId());
            bookList = (ArrayList<CartBook>) query.list();
            connectionDAO.commit();
        } catch (HibernateException e) {
            connectionDAO.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            connectionDAO.close();
        }
        return bookList;
    }

    public int deleteBook(int id) {
        int result = 0;
        ConnectionDAO connectionDAO = new ConnectionDAO();
        try {
            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createQuery("from CartBook where bookId = :id");
            query.setInteger("id", id);
            CartBook cartBook = (CartBook) query.uniqueResult();
            if (cartBook != null) {
                connectionDAO.getSession().delete(cartBook);
                connectionDAO.commit();
                result = 1;
            }
        } catch (HibernateException e) {
            connectionDAO.rollbackTransaction();
            e.printStackTrace();

        } finally {
            connectionDAO.getSession().flush();
            connectionDAO.getSession().clear();
            connectionDAO.close();
        }
        return result;
    }

}
