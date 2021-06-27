/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.controller;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.neu.dao.BookDAO;
import com.neu.metrics.Metrics;
import com.neu.pojo.Seller;
import com.neu.validators.FieldValidations;
import java.io.File;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author abhishek
 */
public class BookController extends AbstractController {

    private static final Logger logger = Logger.getLogger(BookController.class);

    public BookController() {
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
            MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
            String action = multipartRequest.getParameter("action") != null ? multipartRequest.getParameter("action") : "";
            Seller seller = (Seller) multipartRequest.getSession(false).getAttribute("seller");
            AmazonS3 s3client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
            String bucketName = new BookDAO().getBucketName();

            if (action.equals("registerBook") && seller != null) {
                String isbn = multipartRequest.getParameter("isbn") != null ? multipartRequest.getParameter("isbn") : "";
                String title = multipartRequest.getParameter("title") != null ? multipartRequest.getParameter("title") : "";
                String authors = multipartRequest.getParameter("authors") != null ? multipartRequest.getParameter("authors") : "";
                String price = multipartRequest.getParameter("price") != null ? multipartRequest.getParameter("price") : "";
                String quanitity = multipartRequest.getParameter("quanitity") != null ? multipartRequest.getParameter("quanitity") : "";
                String publishDate = multipartRequest.getParameter("publishDate") != null ? multipartRequest.getParameter("publishDate") : "";
                String imageNames = multipartRequest.getFile("images").getOriginalFilename();
                if (!isbn.equalsIgnoreCase("") && !title.equalsIgnoreCase("") && !authors.equalsIgnoreCase("") && !price.equalsIgnoreCase("") && !quanitity.equalsIgnoreCase("") && !publishDate.equalsIgnoreCase("") && !imageNames.equalsIgnoreCase("")) {
                    FieldValidations fv = new FieldValidations();
                    if (fv.validateIsbn(isbn) && fv.validateNames(title) && fv.validateAuthors(authors) && fv.validatePrice(price) && fv.validateQuantity(quanitity) && fv.validateDate(publishDate)) {
                        BookDAO bdao = new BookDAO();
                        int bookId = bdao.addBook(seller, isbn, title, authors, Date.valueOf(publishDate), Integer.parseInt(quanitity), Double.parseDouble(price), imageNames);
                        if (bookId != -1) {

                            metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);

                            MultipartFile multipartFile = multipartRequest.getFile("images");
                            String FILE_TO = System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename();
                            File file = new File(FILE_TO);
                            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
                            String fileName = bookId + "-" + isbn + "-" + System.currentTimeMillis() + "." + multipartFile.getOriginalFilename().split("[.]")[1];
                            PutObjectRequest putObj = new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicReadWrite);
                            ObjectMetadata metaData = new ObjectMetadata();
                            metaData.addUserMetadata("x-amz-meta-originalfilename", multipartFile.getOriginalFilename());
                            metaData.addUserMetadata("x-amz-meta-sellerid", seller.getSellerId() + "");
                            putObj.setMetadata(metaData);
                            s3client.putObject(putObj.withCannedAcl(CannedAccessControlList.PublicReadWrite));
                            file.delete();

                            logger.info("Book Added");
                            metrics.getInstance().recordExecutionTimeToNow("S3-Access", timestart);
                            metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                            modelAndView = new ModelAndView("seller-success", "message", "Book successfully added.");
                        } else {
                            modelAndView = new ModelAndView("seller-error", "message", "Book details not added");
                        }
                    } else {
                        modelAndView = new ModelAndView("seller-error", "message", "Book details not added");
                    }
                } else {
                    modelAndView = new ModelAndView("seller-error", "message", "Book details not added");
                }
            }

            if (action.equals("addMoreImages") && seller != null) {
                String isbn = multipartRequest.getParameter("isbn") != null ? multipartRequest.getParameter("isbn") : "";
                String bookId = multipartRequest.getParameter("bookId") != null ? multipartRequest.getParameter("bookId") : "";
                if (!isbn.equalsIgnoreCase("") && !bookId.equalsIgnoreCase("")) {
                    MultipartFile multipartFile = multipartRequest.getFile("images");
                    String FILE_TO = System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename();
                    File file = new File(FILE_TO);
                    FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
                    String fileName = bookId + "-" + isbn + "-" + System.currentTimeMillis() + "." + multipartFile.getOriginalFilename().split("[.]")[1];
                    Map<String, String> userMetadata = new HashMap<String, String>();
                    userMetadata.put("x-amz-meta-originalfilename", multipartFile.getOriginalFilename());
                    userMetadata.put("x-amz-meta-sellerid", seller.getSellerId() + "");
                    ObjectMetadata metaData = new ObjectMetadata();
                    metaData.setUserMetadata(userMetadata);
                    //s3client.putObject(new PutObjectRequest(bucketName, fileName, file).setMetadata(metaData).withCannedAcl(CannedAccessControlList.PublicRead));
                    PutObjectRequest putObj = new PutObjectRequest(bucketName, fileName, file);
                    putObj.setMetadata(metaData);
                    putObj.withCannedAcl(CannedAccessControlList.PublicReadWrite);
                    s3client.putObject(putObj);
                    file.delete();
                    BookDAO bdao = new BookDAO();
                    int result = bdao.updateImageData(Integer.parseInt(bookId), multipartFile.getOriginalFilename());
                    if (result == 1) {

                        logger.info("More Images to Book Added");
                        metrics.getInstance().recordExecutionTimeToNow("S3-Access", timestart);
                        metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                        metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                        modelAndView = new ModelAndView("seller-success", "message", "Book Image successfully added.");
                    } else {
                        modelAndView = new ModelAndView("seller-error", "message", "Book Image not added");
                    }

                }
            }

        } catch (Exception e) {
            logger.error("Message", e);
            modelAndView = new ModelAndView("seller-error", "message", "Request could not be processed");
        }

        return modelAndView;
    }

}
