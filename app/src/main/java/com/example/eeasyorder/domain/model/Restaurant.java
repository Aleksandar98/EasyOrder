package com.example.eeasyorder.domain.model;

import java.io.Serializable;

public class Restaurant implements Serializable {
    public String name;
    public String description;
    public boolean isOpen;
    public String welcomeMessage;
    public String imageUrl;

    public String distance;
    public String address;
    public String serving_times;

    public Restaurant(String name, String description, boolean isOpen, String welcomeMessage, String imageUrl, String distance, String address, String serving_times) {
        this.name = name;
        this.description = description;
        this.isOpen = isOpen;
        this.welcomeMessage = welcomeMessage;
        this.imageUrl = imageUrl;
        this.distance = distance;
        this.address = address;
        this.serving_times = serving_times;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isOpen=" + isOpen +
                ", welcomeMessage='" + welcomeMessage + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
