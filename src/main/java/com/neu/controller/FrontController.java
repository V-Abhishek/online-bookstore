/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.controller;

import com.neu.dao.SellerDAO;
import com.neu.dao.UserDAO;
import com.neu.metrics.Metrics;
import com.neu.validators.FieldValidations;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author abhishek
 */
public class FrontController extends AbstractController {

    private static final Logger logger = Logger.getLogger(FrontController.class);

    public FrontController() {
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
            if (action.equalsIgnoreCase("register")) {

                metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);
                
                modelAndView = new ModelAndView("register");
            }
            if (action.equalsIgnoreCase("registerWithBoth")) {
                String firstName = request.getParameter("firstname") != null ? request.getParameter("firstname") : "";
                String lastName = request.getParameter("lastname") != null ? request.getParameter("lastname") : "";
                String email = request.getParameter("email") != null ? request.getParameter("email") : "";
                String password = request.getParameter("pwd2") != null ? request.getParameter("pwd2") : "";
                if (!firstName.equalsIgnoreCase("") && !lastName.equalsIgnoreCase("") && !email.equalsIgnoreCase("") && !password.equalsIgnoreCase("")) {
                    FieldValidations fv = new FieldValidations();
                    if (fv.validateNames(firstName) && fv.validateNames(lastName) && fv.validateEmailAddress(email) && fv.validatePassword(password)) {
                        UserDAO userDAO = new UserDAO();
                        SellerDAO sellerDAO = new SellerDAO();
                        int result1 = userDAO.addNewUser(firstName, lastName, email, password);
                        int result2 = sellerDAO.addNewSeller(firstName, lastName, email, password);
                        if (result1 == 1 && result2 == 1) {
                            
                            logger.info("Successfully Registered a new user on site");
                            metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                            metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);
                            
                            modelAndView = new ModelAndView("customer-thank-you", "name", firstName);
                        } else {
                            modelAndView = new ModelAndView("error", "message", "Your registeration data had some issues");
                        }
                    } else {
                        modelAndView = new ModelAndView("error", "message", "Your registeration data had some issues");
                    }

                } else {
                    modelAndView = new ModelAndView("error", "message", "Your registeration data had some issues");
                }
            }
            if (action.equals("logout")) {
                HttpSession session = request.getSession(false);
                if (session.getAttribute("user") != null) {
                    session.removeAttribute("user");
                    session.invalidate();

                }
                if (session.getAttribute("seller") != null) {
                    session.removeAttribute("seller");
                    session.invalidate();

                }
                metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);
            }
        } catch (Exception e) {
            logger.error("Front Controller", e);
        }
        if (modelAndView == null) {
            modelAndView = new ModelAndView("home");
        }
        return modelAndView;
    }

}
