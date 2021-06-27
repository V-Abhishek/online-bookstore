/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.validators;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

/**
 *
 * @author Abhishek
 */
@RunWith(MockitoJUnitRunner.class)
public class FieldValidationsTest {

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void validateEmailTest() {
        String email = "abc@gmail.com";
        try {
            FieldValidations fieldValidations = new FieldValidations();
            boolean output = fieldValidations.validateEmailAddress(email);
            assertTrue(output);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void validateFirstNameTest() {
        String firstname = "abc123";
        try {
            FieldValidations fieldValidations = new FieldValidations();
            boolean output = fieldValidations.validateNames(firstname);
            assertFalse(output);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
