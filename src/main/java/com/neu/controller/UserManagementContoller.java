/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.controller;

import com.neu.dao.UserDAO;
import com.neu.validators.FieldValidations;
import java.io.PrintWriter;
import com.google.gson.JsonObject;
import com.neu.metrics.Metrics;
import com.neu.pojo.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author abhishek
 */
public class UserManagementContoller extends AbstractController {

    public UserManagementContoller() {
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
                User user = (User) request.getSession(false).getAttribute("user");
                if (user != null) {

                    metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                    modelAndView = new ModelAndView("user-home");
                }
            }

//            if (action.equalsIgnoreCase("registerUser")) {
//                String firstName = request.getParameter("firstname") != null ? request.getParameter("firstname") : "";
//                String lastName = request.getParameter("lastname") != null ? request.getParameter("lastname") : "";
//                String email = request.getParameter("email") != null ? request.getParameter("email") : "";
//                String password = request.getParameter("pwd2") != null ? request.getParameter("pwd2") : "";
//                if (!firstName.equalsIgnoreCase("") && !lastName.equalsIgnoreCase("") && !email.equalsIgnoreCase("") && !password.equalsIgnoreCase("")) {
//                    FieldValidations fv = new FieldValidations();
//                    if (fv.validateNames(firstName) && fv.validateNames(lastName) && fv.validateEmailAddress(email) && fv.validatePassword(password)) {
//                        UserDAO userDAO = new UserDAO();
//                        int result = userDAO.addNewUser(firstName, lastName, email, password);
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
            if (action.equalsIgnoreCase("validateCustomer")) {
                String email = request.getParameter("email") != null ? request.getParameter("email") : "";
                if (!email.equalsIgnoreCase("")) {
                    UserDAO udao = new UserDAO();
                    Boolean exist = udao.isUnique(email);
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

                modelAndView = new ModelAndView("user-update");
            }
            if (action.equalsIgnoreCase("updateProfile")) {
                HttpSession session = request.getSession(false);
                if (session != null && session.getAttribute("user") != null) {
                    User user = (User) session.getAttribute("user");
                    String firstName = request.getParameter("firstName") != null ? request.getParameter("firstName") : "";
                    String lastName = request.getParameter("lastName") != null ? request.getParameter("lastName") : "";
                    if (user != null && !firstName.equalsIgnoreCase("") && !lastName.equalsIgnoreCase("")) {
                        FieldValidations fv = new FieldValidations();
                        if (fv.validateNames(firstName) && fv.validateNames(lastName)) {
                            UserDAO udao = new UserDAO();
                            int result = udao.updateUserProfile(user, firstName, lastName);
                            if (result == 1) {
                                request.setAttribute("user", user);

                                metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                                metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                                modelAndView = new ModelAndView("customer-success", "message", "Details updated successfully");
                            } else {
                                modelAndView = new ModelAndView("customer-error", "message", "Details not updated");
                            }
                        } else {
                            modelAndView = new ModelAndView("customer-error", "message", "Details not updated");
                        }
                    } else {
                        modelAndView = new ModelAndView("customer-error", "message", "Details not updated");
                    }
                }
            }
            if (action.equalsIgnoreCase("changePassword")) {

                metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                modelAndView = new ModelAndView("update-password");
            }
            if (action.equalsIgnoreCase("updatePassword")) {
                HttpSession session = request.getSession(false);
                if (session != null && session.getAttribute("user") != null) {
                    User user = (User) session.getAttribute("user");
                    String password = request.getParameter("pwd2") != null ? request.getParameter("pwd2") : "";
                    if (user != null && !password.equalsIgnoreCase("")) {
                        FieldValidations fv = new FieldValidations();
                        if (fv.validatePassword(password)) {
                            UserDAO udao = new UserDAO();
                            int result = udao.updateUserPassword(user, password);
                            if (result == 1) {
                                request.setAttribute("user", user);

                                metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                                metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                                modelAndView = new ModelAndView("customer-success", "message", "Password updated successfully");
                            } else {
                                modelAndView = new ModelAndView("customer-error", "message", "Password not updated");
                            }
                        } else {
                            modelAndView = new ModelAndView("customer-error", "message", "Password not updated");
                        }
                    } else {
                        modelAndView = new ModelAndView("customer-error", "message", "Password not updated");
                    }
                }
            }
            if (action.equalsIgnoreCase("viewProfile")) {
                HttpSession session = request.getSession(false);
                if (session != null && session.getAttribute("user") != null) {
                    User user = (User) session.getAttribute("user");
                    if (user != null) {
                        UserDAO udao = new UserDAO();
                        User u = udao.getUser(user.getId());
                        if (u != null) {
                            System.out.println("Inside");
                            session.removeAttribute("user");
                            session.setAttribute("user", u);

                            metrics.getInstance().recordExecutionTimeToNow("DB-Access", timestart);
                            metrics.getInstance().recordExecutionTimeToNow("Web-Request", timestart);

                            modelAndView = new ModelAndView("user-profile");
                        } else {
                            modelAndView = new ModelAndView("customer-error", "message", "Something Went Wrong!!!");
                        }
                    } else {
                        modelAndView = new ModelAndView("customer-error", "message", "Something Went Wrong!!!");
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return modelAndView;
    }

}
