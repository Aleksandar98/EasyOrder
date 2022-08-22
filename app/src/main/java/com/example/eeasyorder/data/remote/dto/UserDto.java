package com.example.eeasyorder.data.remote.dto;

import com.example.eeasyorder.domain.model.User;

import java.util.ArrayList;

public class UserDto {

    public CustomerAccount customer_account;
    public Token token;

    public class Token{
        public String value;
        public int ttl;
        public int refresh_ttl;
    }

    public class CustomerAccount{
        public int id;
        public int type_id;
        public String reference_type;
        public int customer_id;
        public int brand_id;
        public String first_name;
        public String last_name;
        public String email;
        public boolean confirmed;
        public String phone_number;
        public String locale;
        public int state;
        public ArrayList<Object> demographics;
        public int optin_status_email;
        public int optin_status_pn;
        public String updated_at;
        public String created_at;
    }

    User toUser(){
        return new User(customer_account.first_name, token.value);
    }
}
