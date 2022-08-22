package com.example.eeasyorder.data;

import java.util.regex.Pattern;

public class Utils {

    public static boolean validateEmail(String emailAddress){
        return Pattern.compile("^(.+)@(\\S+)$")
                .matcher(emailAddress)
                .matches();
    }
}
