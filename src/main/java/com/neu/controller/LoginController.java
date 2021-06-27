/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.controller;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.Topic;
import com.neu.dao.SellerDAO;
import com.neu.dao.UserDAO;
import com.neu.metrics.Metrics;
import com.neu.pojo.Seller;
import com.neu.pojo.User;
import java.util.List;
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
public class LoginController extends AbstractController {

    private static final Logger logger = Logger.getLogger(LoginController.class);

    public LoginController() {
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
            if (action.equalsIgnoreCase("signCustomer")) {

                metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                modelAndView = new ModelAndView("login-customer");
            }
            if (action.equalsIgnoreCase("signVendor")) {

                metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                modelAndView = new ModelAndView("login-vendor");
            }
            if (action.equalsIgnoreCase("vendorForgot")) {

                metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                modelAndView = new ModelAndView("vendor-reset");
            }
            if (action.equalsIgnoreCase("customerForgot")) {

                metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                modelAndView = new ModelAndView("customer-reset");
            }
            if (action.equalsIgnoreCase("customerLogin")) {
                String email = request.getParameter("email") != null ? request.getParameter("email") : "";
                String password = request.getParameter("psw") != null ? request.getParameter("psw") : "";
                if (!email.equalsIgnoreCase("") && !password.equalsIgnoreCase("")) {
                    UserDAO userDAO = new UserDAO();
                    User user = userDAO.authenticateUser(email, password);
                    if (user != null) {
                        HttpSession session = request.getSession(true);
                        session.setAttribute("user", user);

                        metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                        metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                        modelAndView = new ModelAndView("user-home");
                    } else {
                        modelAndView = new ModelAndView("error", "message", "Please recheck your Username and Password");
                    }
                } else {
                    modelAndView = new ModelAndView("error", "message", "Please recheck your Username and Password");
                }
            }
            if (action.equalsIgnoreCase("vendorLogin")) {
                String email = request.getParameter("email") != null ? request.getParameter("email") : "";
                String password = request.getParameter("psw") != null ? request.getParameter("psw") : "";
                if (!email.equalsIgnoreCase("") && !password.equalsIgnoreCase("")) {
                    SellerDAO sellerDAO = new SellerDAO();
                    Seller seller = sellerDAO.authenticateSeller(email, password);
                    if (seller != null) {
                        HttpSession session = request.getSession(true);
                        session.setAttribute("seller", seller);

                        metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                        metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                        modelAndView = new ModelAndView("seller-home");
                    } else {
                        modelAndView = new ModelAndView("error", "message", "Please recheck your Username and Password");
                    }
                } else {
                    modelAndView = new ModelAndView("error", "message", "Please recheck your Username and Password");
                }
            }
            if (action.equalsIgnoreCase("VSendLink")) {
                String email = request.getParameter("email") != null ? request.getParameter("email") : "";
                SellerDAO sellerDAO = new SellerDAO();
                if (!email.equalsIgnoreCase("")) {
                    boolean exist = sellerDAO.isUnique(email);
                    if (exist) {
                        AmazonSNS snsClient = AmazonSNSAsyncClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
                        List<Topic> topics = snsClient.listTopics().getTopics();
                        for (Topic topic : topics) {
                            if (topic.getTopicArn().endsWith("password_reset")) {
                                PublishRequest req = new PublishRequest(topic.getTopicArn(), email.trim());
                                snsClient.publish(req);
                                break;
                            }
                        }
                        metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);
                        metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                        modelAndView = new ModelAndView("reset");
                    } else {
                        metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);
                        metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                        modelAndView = new ModelAndView("error", "message", "This Email does not exist");
                    }
                }
            }
            if (action.equalsIgnoreCase("CSendLink")) {
                String email = request.getParameter("email") != null ? request.getParameter("email") : "";
                UserDAO userDAO = new UserDAO();
                if (!email.equalsIgnoreCase("")) {
                    boolean exist = userDAO.isUnique(email);
                    if (exist) {
                        AmazonSNS snsClient = AmazonSNSAsyncClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
                        List<Topic> topics = snsClient.listTopics().getTopics();
                        for (Topic topic : topics) {
                            if (topic.getTopicArn().endsWith("password_reset")) {
                                PublishRequest req = new PublishRequest(topic.getTopicArn(), email.trim());
                                snsClient.publish(req);
                                break;
                            }
                        }
                        metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);
                        metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                        modelAndView = new ModelAndView("reset");
                    } else {
                        metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);
                        metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                        modelAndView = new ModelAndView("error", "message", "This Email does not exist");
                    }
                }
            }
            if (action.equalsIgnoreCase("")) {
                modelAndView = new ModelAndView("home");
            }

        } catch (Exception e) {
            logger.error("Login Controller", e);
        }
        return modelAndView;
    }

}
