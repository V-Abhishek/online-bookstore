/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.neu.pojo.Book;
import com.neu.pojo.Seller;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 *
 * @author abhishek
 */
public class BookDAO {

    //Private AmazonS3 s3client = AmazonS3ClientBuilder.standard().withCredentials(new ProfileCredentialsProvider("dev")).withRegion(Regions.US_EAST_1).build();
    private AmazonS3 s3client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

    public int addBook(Seller s, String isbn, String title, String authors, Date publicationDate, int quantity, double price, String imageNames) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        int result = -1;
        try {
            connectionDAO.beginTransaction();
            if (s != null) {
                Book book = new Book();
                book.setIsbn(isbn);
                book.setTitle(title);
                book.setAuthors(authors);
                book.setPublicationDate(publicationDate);
                book.setQuantity(quantity);
                book.setPrice(price);
                long millis = System.currentTimeMillis();
                java.sql.Timestamp dateTime = new java.sql.Timestamp(millis);
                book.setBookAdded(dateTime);
                book.setLastUpdated(dateTime);
                book.setSeller(s);
                book.setImageNames(imageNames);
                s.getCatalog().add(book);
                result = (int) connectionDAO.getSession().save(book);
//
//                s.getCatalog().add(book);
//                connectionDAO.getSession().saveOrUpdate(s);
                connectionDAO.commit();
            }
        } catch (HibernateException e) {
            connectionDAO.rollbackTransaction();
            e.printStackTrace();
        } finally {
            connectionDAO.close();
        }
        return result;
    }

    public Book getBookByID(int id) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Book book = null;
        try {
            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createQuery("from Book where bookId = :id");
            query.setInteger("id", id);
            book = (Book) query.uniqueResult();
            connectionDAO.commit();
        } catch (HibernateException e) {
            connectionDAO.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            connectionDAO.close();
        }
        return book;
    }

    public ArrayList<Book> getBooks(Seller s) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        ArrayList<Book> bookList = null;
        try {
            Properties properties = new Properties();
            InputStream inputStream;
            inputStream = new FileInputStream("/home/ubuntu" + "/configuaration.properties");
            properties.load(inputStream);
            inputStream.close();
            String dbName = properties.getProperty("dbName");

            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createSQLQuery("Select * from " + dbName + ".books where seller_id = :id").addEntity(Book.class);
            query.setInteger("id", s.getSellerId());
            bookList = (ArrayList<Book>) query.list();
            connectionDAO.commit();
        } catch (HibernateException e) {
            connectionDAO.rollbackTransaction();
            System.out.println(e.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            connectionDAO.close();
        }
        return bookList;
    }

    public ArrayList<Book> getBooks() {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        ArrayList<Book> bookList = null;
        try {
            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createQuery("From Book");
            bookList = (ArrayList<Book>) query.list();
            connectionDAO.commit();
        } catch (HibernateException e) {
            connectionDAO.rollbackTransaction();
            System.out.println(e.getMessage());
        } finally {
            connectionDAO.close();
        }
        return bookList;
    }

    public int updateBook(int id, String isbn, String title, String authors, Date publicationDate, int quantity, double price) {
        int result = 0;
        ConnectionDAO connectionDAO = new ConnectionDAO();
        try {
            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createQuery("from Book where bookId = :id");
            query.setInteger("id", id);
            Book book = (Book) query.uniqueResult();
            if (book != null) {
                book.setIsbn(isbn);
                book.setTitle(title);
                book.setAuthors(authors);
                book.setPublicationDate(publicationDate);
                book.setQuantity(quantity);
                book.setPrice(price);
                long millis = System.currentTimeMillis();
                java.sql.Timestamp dateTime = new java.sql.Timestamp(millis);
                book.setLastUpdated(dateTime);
                connectionDAO.getSession().update(book);
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

    public int deleteBook(int id) {
        int result = 0;
        ConnectionDAO connectionDAO = new ConnectionDAO();
        try {
            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createQuery("from Book where bookId = :id");
            query.setInteger("id", id);
            Book book = (Book) query.uniqueResult();
            if (book != null) {
                connectionDAO.getSession().delete(book);
                connectionDAO.commit();
                deleteImage(id);
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

    public ArrayList<Book> searchByBookName(String title) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        ArrayList<Book> bookList = null;
        try {
            Properties properties = new Properties();
            InputStream inputStream;
            inputStream = new FileInputStream("/home/ubuntu" + "/configurartion.properties");
            properties.load(inputStream);
            inputStream.close();
            String dbName = properties.getProperty("dbName");

            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createSQLQuery("SELECT * FROM " + dbName + ".books WHERE title = :title AND quantity>0").addEntity(Book.class);
            query.setString("title", title);
            bookList = (ArrayList<Book>) query.list();
            connectionDAO.commit();
        } catch (HibernateException e) {
            connectionDAO.rollbackTransaction();
            System.out.println(e.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            connectionDAO.close();
        }
        return bookList;
    }

    public Map<String, String> getImageUrls(int bookId, String isbn) {
        Map<String, String> imagesMap = new HashMap<String, String>();
        String bucketName = getBucketName();
        ObjectListing objectListing = s3client.listObjects(bucketName);
        List<S3ObjectSummary> objects = objectListing.getObjectSummaries();
        for (S3ObjectSummary os : objects) {
            if (os.getKey().contains(bookId + "-" + isbn)) {
                imagesMap.put(os.getKey(), s3client.getUrl(bucketName, os.getKey()).toExternalForm());
            }
        }

        return imagesMap;
    }

    public ArrayList<String> getImageUrls(String isbn) {
        ArrayList<String> imagesList = new ArrayList<String>();
        String bucketName = getBucketName();
        ObjectListing objectListing = s3client.listObjects(bucketName);
        List<S3ObjectSummary> objects = objectListing.getObjectSummaries();
        for (S3ObjectSummary os : objects) {
            if (os.getKey().contains("-" + isbn + "-")) {
                imagesList.add(s3client.getUrl(bucketName, os.getKey()).toExternalForm());
            }
        }

        return imagesList;
    }

    public int deleteImage(int bookId, String imageKey) {
        int result = 0;
        String bucketName = getBucketName();
        ObjectMetadata objectMetadata = s3client.getObjectMetadata(bucketName, imageKey);
        String fileName = objectMetadata.getUserMetaDataOf("x-amz-meta-originalfilename");
        int resultA = deleteImageData(bookId, fileName);
        if (resultA == 1) {
            s3client.deleteObject(bucketName, imageKey);
            result = 1;
        }
        return result;
    }

    public int updateImageData(int bookId, String bookName) {
        int result = 0;
        ConnectionDAO connectionDAO = new ConnectionDAO();
        try {
            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createQuery("from Book where bookId = :id");
            query.setInteger("id", bookId);
            Book book = (Book) query.uniqueResult();
            if (book != null) {
                String imageNames = book.getImageNames();
                if (imageNames.equalsIgnoreCase("")) {
                    imageNames = imageNames + bookName;
                } else {
                    imageNames = imageNames + "," + bookName;
                }
                book.setImageNames(imageNames);
                long millis = System.currentTimeMillis();
                java.sql.Timestamp dateTime = new java.sql.Timestamp(millis);
                book.setLastUpdated(dateTime);
                connectionDAO.getSession().update(book);
                connectionDAO.commit();
                result = 1;
            }
        } catch (HibernateException e) {
            connectionDAO.rollbackTransaction();
            e.printStackTrace();
        } finally {
            connectionDAO.close();
        }
        return result;
    }

    public int deleteImageData(int bookId, String bookName) {
        int result = 0;
        ConnectionDAO connectionDAO = new ConnectionDAO();
        try {
            connectionDAO.beginTransaction();
            Query query = null;
            query = connectionDAO.getSession().createQuery("from Book where bookId = :id");
            query.setInteger("id", bookId);
            Book book = (Book) query.uniqueResult();
            if (book != null) {
                String imageNames = book.getImageNames();
                if (!imageNames.equalsIgnoreCase("")) {
                    String newImageNames = "";
                    ArrayList<String> oldNamesList = new ArrayList(Arrays.asList(imageNames.split(",")));
                    Iterator<String> itr = oldNamesList.iterator();
                    while (itr.hasNext()) {
                        String image = itr.next();
                        if (image.equals(bookName)) {
                            itr.remove();
                        }
                    }
                    if (!oldNamesList.isEmpty()) {
                        for (int i = 0; i < oldNamesList.size(); i++) {
                            if (i == 0) {
                                newImageNames = newImageNames + oldNamesList.get(i);
                            } else {
                                newImageNames = newImageNames + "," + oldNamesList.get(i);
                            }
                        }
                    }
                    book.setImageNames(newImageNames);
                }
                long millis = System.currentTimeMillis();
                java.sql.Timestamp dateTime = new java.sql.Timestamp(millis);
                book.setLastUpdated(dateTime);
                connectionDAO.getSession().update(book);
                connectionDAO.commit();
                result = 1;
            }
        } catch (HibernateException e) {
            connectionDAO.rollbackTransaction();
            e.printStackTrace();
        } finally {
            connectionDAO.close();
        }
        return result;
    }

    public void deleteImage(int bookId) {

        ArrayList<String> imagesList = new ArrayList<String>();
        String bucketName = getBucketName();
        ObjectListing objectListing = s3client.listObjects(bucketName);
        List<S3ObjectSummary> objects = objectListing.getObjectSummaries();
        for (S3ObjectSummary os : objects) {
            if (os.getKey().contains(bookId + "-")) {
                s3client.deleteObject(bucketName, os.getKey());
            }
        }
    }

    public String getBucketName() {
        String bucketName = "";
        try {
            Properties properties = new Properties();
            InputStream inputStream;
            inputStream = new FileInputStream("/home/ubuntu" + "/configuaration.properties");
            properties.load(inputStream);
            inputStream.close();
            bucketName = properties.getProperty("bucketName");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return bucketName;
    }

}
