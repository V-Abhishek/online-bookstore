/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.dao;

import com.neu.pojo.User;
import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author abhishek
 */
public class UserDAO {

    public boolean isUnique(String email) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        boolean exist = false;
        try {
            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createQuery("FROM User WHERE email = :email");
            query.setString("email", email);
            ArrayList<User> userList = (ArrayList<User>) query.list();
            connectionDAO.commit();
            if (!userList.isEmpty()) {
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

    public int addNewUser(String firstName, String lastName, String email, String password) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        int result = 0;
        try {
            connectionDAO.beginTransaction();
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            connectionDAO.getSession().save(user);
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

    public User authenticateUser(String email, String password) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        User user = null;
        Boolean valid = false;
        try {
            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createQuery("FROM User WHERE email = :email");
            query.setString("email", email);
            user = (User) query.uniqueResult();
            connectionDAO.commit();
            if (user != null) {
                valid = checkPass(password, user.getPassword());
                if (!valid) {
                    user = null;
                }
            }
        } catch (HibernateException e) {
            connectionDAO.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            connectionDAO.close();
        }
        return user;
    }

    private boolean checkPass(String plainPassword, String hashedPassword) {
        boolean valid = false;
        if (BCrypt.checkpw(plainPassword, hashedPassword)) {
            valid = true;
        }
        return valid;
    }

    public int updateUserProfile(User user, String firstName, String lastName) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        int id = user.getId();
        int result = 0;
        try {
            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createQuery("FROM User WHERE id = :id");
            query.setInteger("id", id);
            user = (User) query.uniqueResult();
            if (user != null) {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                connectionDAO.getSession().update(user);
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
    
    public int updateUserPassword(User user, String password) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        int id = user.getId();
        int result = 0;
        try {
            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createQuery("FROM User WHERE id = :id");
            query.setInteger("id", id);
            user = (User) query.uniqueResult();
            if (user != null) {
                user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
                connectionDAO.getSession().update(user);
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
    
    public User getUser(int userId) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        User user = null;
        try {
            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createQuery("FROM User WHERE id = :userId");
            query.setInteger("userId", userId);
            user = (User) query.uniqueResult();
            connectionDAO.commit();
        } catch (HibernateException e) {
            connectionDAO.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            connectionDAO.close();
        }
        return user;
    }

}
