package com.example.demo2.service;

import java.util.ArrayList;

import com.example.demo2.model.Order;

public interface OrderService {
    ArrayList<Order> getAllOrder();
    Order getOrderById(int id);
    int addNewOrder(Order order);
    int updateExistingOrder(Order order);
    int deleteExistingOrder(int id);
}
