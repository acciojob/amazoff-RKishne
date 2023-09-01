package com.driver;

import io.swagger.models.auth.In;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id=id;
        String[] time=deliveryTime.split(":");
        String HH=String.valueOf(Integer.parseInt(time[0])/60);
        String MM=String.valueOf(Integer.parseInt(time[1])%60);
        this.deliveryTime=(Integer.parseInt(HH))*60 + Integer.parseInt(MM);;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
