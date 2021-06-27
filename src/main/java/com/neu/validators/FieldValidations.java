/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.validators;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author abhishek
 */
public class FieldValidations {

    public boolean validateNames(String s) {
        Pattern p = Pattern.compile("[a-zA-Z]+([\\s][a-zA-Z]+)*");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public boolean validateEmailAddress(String s) {
        Pattern p = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{1,63}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public boolean validatePassword(String s) {
        Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*]).{8,16}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public boolean validateIsbn(String s) {
        Pattern p = Pattern.compile("^(97(8|9))?\\d{9}(\\d|X)$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public boolean validateDate(String s) {
        Pattern p = Pattern.compile("^(19|20)\\d\\d([- /.])(0[1-9]|1[012])\\2(0[1-9]|[12][0-9]|3[01])$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public boolean validatePrice(String s) {
        try {
            double price = DecimalFormat.getNumberInstance().parse(s).doubleValue();
            if (price >= 0.01 && price <= 9999.99) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean validateQuantity(String s) {
        try {
            int quantity = Integer.parseInt(s);
            if (quantity >= 0 && quantity <= 999) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean validateAuthors(String s) {
        Pattern p = Pattern.compile("[a-zA-Z\\s+]+(,[a-zA-Z\\s+]+)*");
        Matcher m = p.matcher(s);
        return m.matches();
    }
}
