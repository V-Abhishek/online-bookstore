/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.controller;

import com.neu.dao.BookDAO;
import com.neu.metrics.Metrics;
import com.neu.pojo.Book;
import com.neu.pojo.Seller;
import com.neu.validators.FieldValidations;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author abhishek
 */
public class SellerInventoryController extends AbstractController {

    private static final Logger logger = Logger.getLogger(SellerInventoryController.class);

    public SellerInventoryController() {
    }

    private Metrics metrics;

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = null;
        Long timestart = System.currentTimeMillis();
        try {
            String action = request.getParameter("action") != null ? request.getParameter("action") : "";
            Seller seller = (Seller) request.getSession(false).getAttribute("seller");

            if (action.equals("") && seller != null) {

                metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                modelAndView = new ModelAndView("seller-home");
            }

            if (action.equalsIgnoreCase("addBook") && seller != null) {

                metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                modelAndView = new ModelAndView("book-add");
            }

            if (action.equalsIgnoreCase("update") && seller != null) {
                String bookId = request.getParameter("id") != null ? request.getParameter("id") : "";
                if (!bookId.equalsIgnoreCase("")) {
                    BookDAO bdao = new BookDAO();
                    Book book = bdao.getBookByID(Integer.parseInt(bookId));
                    if (book != null) {
                        Map<String, String> imagesMap = bdao.getImageUrls(book.getBookId(), book.getIsbn());
                        request.setAttribute("imagesMap", imagesMap);

                        metrics.getInstance().recordExecutionTimeToNow("S3-Access", timestart);
                        metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                        metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                        modelAndView = new ModelAndView("book-update", "book", book);
                    } else {
                        modelAndView = new ModelAndView("seller-error", "message", "Book you requested for does not exist");
                    }
                }
            }

            if (action.equals("updateBook") && seller != null) {
                String bookId = request.getParameter("id") != null ? request.getParameter("id") : "";
                String isbn = request.getParameter("isbn") != null ? request.getParameter("isbn") : "";
                String title = request.getParameter("title") != null ? request.getParameter("title") : "";
                String authors = request.getParameter("authors") != null ? request.getParameter("authors") : "";
                String price = request.getParameter("price") != null ? request.getParameter("price") : "";
                String quanitity = request.getParameter("quanitity") != null ? request.getParameter("quanitity") : "";
                String publishDate = request.getParameter("publishDate") != null ? request.getParameter("publishDate") : "";
                if (!bookId.equalsIgnoreCase("") && !isbn.equalsIgnoreCase("") && !title.equalsIgnoreCase("") && !authors.equalsIgnoreCase("") && !price.equalsIgnoreCase("") && !quanitity.equalsIgnoreCase("") && !publishDate.equalsIgnoreCase("")) {
                    FieldValidations fv = new FieldValidations();
                    if (fv.validateIsbn(isbn) && fv.validateNames(title) && fv.validateAuthors(authors) && fv.validatePrice(price) && fv.validateQuantity(quanitity) && fv.validateDate(publishDate)) {
                        BookDAO bdao = new BookDAO();
//                        String[] as = authors.split(",");
//                        List<String> authorList = Arrays.asList(as);
                        int result = bdao.updateBook(Integer.parseInt(bookId), isbn, title, authors, Date.valueOf(publishDate), Integer.parseInt(quanitity), Double.parseDouble(price));
                        if (result == 1) {

                            metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                            metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                            modelAndView = new ModelAndView("seller-success", "message", "Book successfully updated.");
                        } else {
                            modelAndView = new ModelAndView("seller-error", "message", "Book details not updated");
                        }
                    } else {
                        modelAndView = new ModelAndView("seller-error", "message", "Book details not updated");
                    }
                } else {
                    modelAndView = new ModelAndView("seller-error", "message", "Book details not updated");
                }
            }

            if (action.equals("delete") && seller != null) {
                String bookId = request.getParameter("id") != null ? request.getParameter("id") : "";
                if (!bookId.equalsIgnoreCase("")) {
                    BookDAO bdao = new BookDAO();
                    int result = bdao.deleteBook(Integer.parseInt(bookId));
                    if (result == 1) {

                        logger.info("A Book was deleted");
                        metrics.getInstance().recordExecutionTimeToNow("S3-Access", timestart);
                        metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                        metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                        modelAndView = new ModelAndView("seller-success", "message", "Book details successfully deleted.");
                    } else {
                        modelAndView = new ModelAndView("seller-error", "message", "Book details not deleted");
                    }
                } else {
                    modelAndView = new ModelAndView("seller-error", "message", "Book details not deleted");
                }
            }

            if (action.equals("viewInventory") && seller != null) {
                BookDAO bdao = new BookDAO();
                ArrayList<Book> requestList = bdao.getBooks(seller);

                metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                modelAndView = new ModelAndView("seller-inventory", "requestList", requestList);
            }

            if (action.equals("deleteImage") && seller != null) {
                String imageKey = request.getParameter("imageKey") != null ? request.getParameter("imageKey") : "";
                String bookId = request.getParameter("bookId") != null ? request.getParameter("bookId") : "";
                if (!imageKey.equalsIgnoreCase("") && !bookId.equalsIgnoreCase("")) {
                    BookDAO bdao = new BookDAO();
                    int result = bdao.deleteImage(Integer.parseInt(bookId), imageKey);
                    if (result == 1) {

                        logger.info("A Book Image was deleted");
                        metrics.getInstance().recordExecutionTimeToNow("S3-Access", timestart);
                        metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                        metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                        modelAndView = new ModelAndView("seller-success", "message", "Book Image successfully deleted.");
                    } else {
                        modelAndView = new ModelAndView("seller-error", "message", "Book details not deleted");
                    }

                } else {
                    modelAndView = new ModelAndView("seller-error", "message", "Book details not deleted");
                }
            }

        } catch (Exception e) {
            logger.error(e);
            System.out.println(e.getMessage());
        }
        return modelAndView;

    }

}
