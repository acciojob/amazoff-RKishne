package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {

    Map<String,Order> ordersDB =new HashMap<>();
    Map<String,DeliveryPartner> deliveryPartnersDB =new HashMap<>();
    Map<String,String> orderPartnerDB =new HashMap<>();
    Map<String , List<String>> partnerOrdersDB =new HashMap<>();


    public void addOrder(Order order) {
        ordersDB.put(order.getId(),order);
    }

    public void addPartner(String partnerId) {
        deliveryPartnersDB.put(partnerId,new DeliveryPartner(partnerId));
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        if(ordersDB.containsKey(orderId) && deliveryPartnersDB.containsKey(partnerId)){
            orderPartnerDB.put(orderId,partnerId);

            List<String> currentOrders=new ArrayList<>();

            if(partnerOrdersDB.containsKey(partnerId)){
                currentOrders=partnerOrdersDB.get(partnerId);
            }
            currentOrders.add(orderId);
            partnerOrdersDB.put(partnerId,currentOrders);

            DeliveryPartner deliveryPartner=deliveryPartnersDB.get(partnerId);
            deliveryPartner.setNumberOfOrders(currentOrders.size());
        }
    }

    public Order getOrderById(String orderId) {
        return ordersDB.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return deliveryPartnersDB.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return partnerOrdersDB.get(partnerId).size();
    }

    public List<String> getOrderByPartnerId(String partnerId) {
        return partnerOrdersDB.get(partnerId);
    }

    public List<String> getAllOrders() {
        List<String> orders=new ArrayList<>();
        for(String order:ordersDB.keySet()){
            orders.add(order);
        }
        return orders;
    }

    public Integer getCountOfUnassignedOrders() {
        return ordersDB.size()-orderPartnerDB.size();
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(int newTime, String partnerId) {
        int count=0;
        List<String> orders=partnerOrdersDB.get(partnerId);
        for(String orderId: orders){
            int deliveryTime=ordersDB.get(orderId).getDeliveryTime();
            if(deliveryTime>newTime){
                count++;
            }
        }
        return count;
    }
    public int getLastDeliveryTimeByPartnerId(String partnerId) {
        int maxTime=0;
        List<String> orders=partnerOrdersDB.get(partnerId);
        for(String orderID:orders){
            int deliveryTime=ordersDB.get(orderID).getDeliveryTime();
            maxTime=Math.max(maxTime,deliveryTime);
        }
        return maxTime;
    }
    public void deletePartnerById(String partnerId) {
        deliveryPartnersDB.remove(partnerId);
        List<String> listOfOrders=partnerOrdersDB.get(partnerId);
        partnerOrdersDB.remove(partnerId);

        for(String order:listOfOrders){
            orderPartnerDB.remove(order);
        }
    }

    public void deleteOrderById(String orderId) {
        ordersDB.remove(orderId);
        String partnerId=orderPartnerDB.get(orderId);
        orderPartnerDB.remove(orderId);

        partnerOrdersDB.get(partnerId).remove(orderId);
        deliveryPartnersDB.get(partnerId).setNumberOfOrders(partnerOrdersDB.get(partnerId).size());
    }



}
