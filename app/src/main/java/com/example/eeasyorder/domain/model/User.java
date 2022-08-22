package com.example.eeasyorder.domain.model;

import java.io.Serializable;

public class User implements Serializable {

    public String first_name;
    public String tokenValue;

    public User(String first_name, String tokenValue) {
        this.first_name = first_name;
        this.tokenValue = tokenValue;
    }

}
