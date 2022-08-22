package com.example.eeasyorder.domain.model;

public class ErrorLogin {
    public InfoMessage validations;
    public String message;
    public String line;
    public String file;
    public String error_id;

    public class InfoMessage {
        public String title;
        public String body;
    }
}
