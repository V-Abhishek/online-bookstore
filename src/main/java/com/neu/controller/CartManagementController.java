/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.controller;

import com.neu.dao.BookDAO;
import com.neu.pojo.User;
import com.neu.dao.ComapanyInventoryDAO;
import com.neu.metrics.Metrics;
import com.neu.pojo.Book;
import com.neu.pojo.CartBook;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author abhishek
 */
public class CartManagementController extends AbstractController {

    private static final Logger logger = Logger.getLogger(CartManagementController.class);

    private Metrics metrics;

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    public CartManagementController() {
    }

    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = null;
        Long timestart = System.currentTimeMillis();
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        User user = (User) request.getSession(false).getAttribute("user");

        if (action.equalsIgnoreCase("viewBooks") && user != null) {
            ComapanyInventoryDAO comapanyInventoryDAO = new ComapanyInventoryDAO();
            ArrayList<Book> requestList = comapanyInventoryDAO.getBooks(user);

            metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
            metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

            modelAndView = new ModelAndView("book-view-all", "requestList", requestList);
        }

        if (action.equalsIgnoreCase("decide") && user != null) {
            String bookId = request.getParameter("id") != null ? request.getParameter("id") : "";
            if (!bookId.equalsIgnoreCase("")) {
                BookDAO bdao = new BookDAO();
                Book book = bdao.getBookByID(Integer.parseInt(bookId));
                if (book != null) {
                    ArrayList<String> imageList = bdao.getImageUrls(book.getIsbn());
                    request.setAttribute("imageList", imageList);
                    //StatsDClient statsd = new NonBlockingStatsDClient("test", "127.0.0.1", 8125);
                    metrics.getInstance().incrementCounter("BookCount-" + book.getTitle());
                    metrics.getInstance().recordExecutionTimeToNow("S3-Access", timestart);
                    metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                    metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                    modelAndView = new ModelAndView("cart-add-book", "book", book);
                } else {
                    modelAndView = new ModelAndView("customer-error", "message", "Book you requested for does not exist");
                }

            }
        }
        /*
        if (action.equalsIgnoreCase("addToCart")) {
            String bookIds = request.getParameter("bookIds");
            if (!bookIds.isEmpty()) {
                ArrayList<Integer> idAndQuantity = new ArrayList<Integer>();
                ArrayList<Integer> bookids = new ArrayList<Integer>();
                ArrayList<Integer> quantity = new ArrayList<Integer>();
                String[] aboutTo = bookIds.split(",");
                for (String string : aboutTo) {
                    idAndQuantity.add(Integer.parseInt(string));
                }
                for (int i = 0; i < idAndQuantity.size(); i++) {
                    if (i % 2 == 0) {
                        bookids.add(idAndQuantity.get(i));
                    } else {
                        quantity.add(idAndQuantity.get(i));
                    }
                }
                ArrayList<CartBook> requestList = new ArrayList<CartBook>();
                BookDAO bookDAO = new BookDAO();
                for (int i = 0; i < bookids.size(); i++) {
                    Book book = bookDAO.getBookByID(bookids.get(i));
                    CartBook cartBook = new CartBook();
                    cartBook.setUserId(user.getId());
                    cartBook.setBookId(book.getBookId());
                    cartBook.setTitle(book.getTitle());
                    cartBook.setPrice(book.getPrice());
                    cartBook.setSeller(book.getSeller());
                    cartBook.setSellerId(book.getSeller().getSellerId());
                    cartBook.setQuantity(quantity.get(i));
                    cartBook.setVendorMail(book.getSeller().getEmail());
                    requestList.add(cartBook);
                }
                ComapanyInventoryDAO comapanyInventoryDAO = new ComapanyInventoryDAO();
                int result = comapanyInventoryDAO.placeToMyCart(requestList);
                if (result == 1) {
                    HashMap requestMap = new HashMap<Integer, Integer>();
                    for (CartBook cartBook : requestList) {
                        requestMap.put(cartBook.getBookId(), cartBook.getQuantity());
                    }
                    HttpSession session = request.getSession(false);
                    session.setAttribute("requestMap", requestMap);
                    modelAndView = new ModelAndView("cart", "requestList", requestList);
                }
            } else {
                modelAndView = new ModelAndView("error", "message", "Something went wrong!!!!");
            }

        }*/

        if (action.equalsIgnoreCase("addToCart")) {
            String id = request.getParameter("id") != null ? request.getParameter("id") : "";
            String requiredQuantity = request.getParameter("requiredQuantity") != null ? request.getParameter("requiredQuantity") : "";
            if (!id.equalsIgnoreCase("") && !requiredQuantity.equalsIgnoreCase("")) {
                BookDAO bookDAO = new BookDAO();
                Book book = bookDAO.getBookByID(Integer.parseInt(id));
                CartBook cartBook = new CartBook();
                cartBook.setUserId(user.getId());
                cartBook.setBookId(book.getBookId());
                cartBook.setTitle(book.getTitle());
                cartBook.setPrice(book.getPrice());
                cartBook.setSeller(book.getSeller());
                cartBook.setSellerId(book.getSeller().getSellerId());
                cartBook.setQuantity(Integer.parseInt(requiredQuantity));
                cartBook.setVendorMail(book.getSeller().getEmail());
                ComapanyInventoryDAO comapanyInventoryDAO = new ComapanyInventoryDAO();
                int result = comapanyInventoryDAO.placeToMyCart(cartBook);
                if (result == 1) {

                    logger.info("Added a book to user cart");
                    ArrayList<Book> requestList = comapanyInventoryDAO.getBooks(user);

                    metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                    metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                    modelAndView = new ModelAndView("book-view-all", "requestList", requestList);
                } else {
                    modelAndView = new ModelAndView("customer-error", "message", "Book you requested for does not exist anymore");
                }
            } else {
                modelAndView = new ModelAndView("customer-error", "message", "Book you requested for does not exist");
            }
        }

        if (action.equalsIgnoreCase("myCart") && user != null) {
            ComapanyInventoryDAO comapanyInventoryDAO = new ComapanyInventoryDAO();
            ArrayList<CartBook> currentList = comapanyInventoryDAO.getUnOrderedBooks(user);
            BookDAO bdao = new BookDAO();
            ArrayList<Book> checkList = bdao.getBooks();
            String changedCartMsg = "";
            ArrayList<CartBook> requestList = new ArrayList<CartBook>();
            for (CartBook cartBook : currentList) {
                boolean notFound = true;
                for (Book book : checkList) {
                    if (cartBook.getBookId() == book.getBookId()) {
                        notFound = false;
                        break;
                    }
                }
                if (notFound) {
                    changedCartMsg = changedCartMsg + " " + cartBook.getTitle() + " is no more available";
                    comapanyInventoryDAO.deleteBook(cartBook.getBookId());
                } else {
                    requestList.add(cartBook);
                }
            }
            request.setAttribute("changedCartMsg", changedCartMsg);

            metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
            metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

            modelAndView = new ModelAndView("cart", "requestList", requestList);
        }
        return modelAndView;
    }

}
