package com.example.eeasyorder.data.remote.dto;

public class ResponseDto{
    public String status;
    public int code;
    public Object data;

    public ResponseDto(String status, int code, Object data) {
        this.status = status;
        this.code = code;
        this.data = data;
    }
}
