/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.controller;

import com.google.gson.JsonObject;
import com.neu.dao.SellerDAO;
import com.neu.metrics.Metrics;
import com.neu.pojo.Seller;
import com.neu.validators.FieldValidations;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author abhishek
 */
public class SellerManagementController extends AbstractController {

    public SellerManagementController() {
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
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        try {
            if (action.equalsIgnoreCase("")) {
                Seller seller = (Seller) request.getSession(false).getAttribute("seller");
                if (seller != null) {

                    metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                    modelAndView = new ModelAndView("seller-home");
                }
            }
//            if (action.equalsIgnoreCase("registerSeller")) {
//                String firstName = request.getParameter("firstname") != null ? request.getParameter("firstname") : "";
//                String lastName = request.getParameter("lastname") != null ? request.getParameter("lastname") : "";
//                String email = request.getParameter("email") != null ? request.getParameter("email") : "";
//                String password = request.getParameter("pwd2") != null ? request.getParameter("pwd2") : "";
//                if (!firstName.equalsIgnoreCase("") && !lastName.equalsIgnoreCase("") && !email.equalsIgnoreCase("") && !password.equalsIgnoreCase("")) {
//                    FieldValidations fv = new FieldValidations();
//                    if (fv.validateNames(firstName) && fv.validateNames(lastName) && fv.validateEmailAddress(email) && fv.validatePassword(password)) {
//                        SellerDAO sellerDAO = new SellerDAO();
//                        int result = sellerDAO.addNewSeller(firstName, lastName, email, password);
//                        if (result == 1) {
//                            modelAndView = new ModelAndView("customer-thank-you", "name", firstName);
//                        } else {
//                            modelAndView = new ModelAndView("error", "message", "Your registeration data had some issues");
//                        }
//                    } else {
//                        modelAndView = new ModelAndView("error", "message", "Your registeration data had some issues");
//                    }
//
//                } else {
//                    modelAndView = new ModelAndView("error", "message", "Your registeration data had some issues");
//                }
//            }
            if (action.equalsIgnoreCase("validateSeller")) {
                String email = request.getParameter("email") != null ? request.getParameter("email") : "";
                if (!email.equalsIgnoreCase("")) {
                    SellerDAO sellerDAO = new SellerDAO();
                    Boolean exist = sellerDAO.isUnique(email);
                    JsonObject result = new JsonObject();
                    if (exist) {
                        result.addProperty("exists", Boolean.TRUE);
                    } else {
                        result.addProperty("exists", Boolean.FALSE);
                    }
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.print(result);
                    out.flush();
                }
            }
            if (action.equalsIgnoreCase("changeProfile")) {

                metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                modelAndView = new ModelAndView("seller-update");
            }
            if (action.equalsIgnoreCase("updateProfile")) {
                HttpSession session = request.getSession(false);
                if (session != null && session.getAttribute("seller") != null) {
                    Seller seller = (Seller) session.getAttribute("seller");
                    String firstName = request.getParameter("firstName") != null ? request.getParameter("firstName") : "";
                    String lastName = request.getParameter("lastName") != null ? request.getParameter("lastName") : "";
                    if (seller != null && !firstName.equalsIgnoreCase("") && !lastName.equalsIgnoreCase("")) {
                        FieldValidations fv = new FieldValidations();
                        if (fv.validateNames(firstName) && fv.validateNames(lastName)) {
                            SellerDAO sellerDAO = new SellerDAO();
                            int result = sellerDAO.updateSellerProfile(seller, firstName, lastName);
                            if (result == 1) {
                                request.setAttribute("seller", seller);

                                metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                                metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                                modelAndView = new ModelAndView("seller-success", "message", "Details updated successfully");
                            } else {
                                modelAndView = new ModelAndView("seller-error", "message", "Details not updated");
                            }
                        } else {
                            modelAndView = new ModelAndView("seller-error", "message", "Details not updated");
                        }
                    } else {
                        modelAndView = new ModelAndView("seller-error", "message", "Details not updated");
                    }
                }
            }
            if (action.equalsIgnoreCase("changePassword")) {

                metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                modelAndView = new ModelAndView("seller-update-password");
            }
            if (action.equalsIgnoreCase("updatePassword")) {
                HttpSession session = request.getSession(false);
                if (session != null && session.getAttribute("seller") != null) {
                    Seller seller = (Seller) session.getAttribute("seller");
                    String password = request.getParameter("pwd2") != null ? request.getParameter("pwd2") : "";
                    if (seller != null && !password.equalsIgnoreCase("")) {
                        FieldValidations fv = new FieldValidations();
                        if (fv.validatePassword(password)) {
                            SellerDAO sellerDAO = new SellerDAO();
                            int result = sellerDAO.updateSellerPassword(seller, password);
                            if (result == 1) {
                                request.setAttribute("seller", seller);

                                metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                                metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                                modelAndView = new ModelAndView("seller-success", "message", "Password updated successfully");
                            } else {
                                modelAndView = new ModelAndView("seller-error", "message", "Password not updated");
                            }
                        } else {
                            modelAndView = new ModelAndView("seller-error", "message", "Password not updated");
                        }
                    } else {
                        modelAndView = new ModelAndView("seller-error", "message", "Password not updated");
                    }
                }
            }
            if (action.equalsIgnoreCase("viewProfile")) {
                HttpSession session = request.getSession(false);
                if (session != null && session.getAttribute("seller") != null) {
                    Seller seller = (Seller) session.getAttribute("seller");
                    if (seller != null) {
                        SellerDAO sellerDAO = new SellerDAO();
                        Seller s = sellerDAO.getSeller(seller.getSellerId());
                        if (s != null) {
                            session.removeAttribute("seller");
                            session.setAttribute("seller", s);

                            metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                            metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                            modelAndView = new ModelAndView("seller-profile");
                        } else {
                            modelAndView = new ModelAndView("seller-error", "message", "Something Went Wrong!!!");
                        }
                    } else {
                        modelAndView = new ModelAndView("seller-error", "message", "Something Went Wrong!!!");
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return modelAndView;
    }

}
