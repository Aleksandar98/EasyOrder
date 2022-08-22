package com.example.eeasyorder;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.eeasyorder.data.Utils;


public class UtilsTest {
    @Test
    public void email_validation_isCorrect() {
        assertTrue(Utils.validateEmail("test@testmenu.app"));
        assertTrue(Utils.validateEmail("jasdoasd123@asdjap.com"));
        assertFalse(Utils.validateEmail("jasdoasd123asdjap.com"));
    }
}