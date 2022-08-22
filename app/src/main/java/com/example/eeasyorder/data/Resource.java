package com.example.eeasyorder.data;

public class Resource<T> {
    public T data = null;
    public String message = null;

    public Resource(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static class Success<T> extends Resource<T> {
        public Success(T data) {
            super(data,null);
        }
    }

    public static class Error<T> extends Resource<T> {
        public Error(String message) {
            super(null,message);
        }
    }

    public static class Loading<T> extends Resource<T> {
        public Loading() {
            super(null,null);
        }
    }
}
