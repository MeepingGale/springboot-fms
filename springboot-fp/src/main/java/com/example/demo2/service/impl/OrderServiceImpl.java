package com.example.demo2.service.impl;

import java.util.ArrayList;

import com.example.demo2.mapper.OrderMapper;
import com.example.demo2.model.Order;
import com.example.demo2.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("OrderServiceImpl")
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderMapper orderMapper;

    @Override
    public ArrayList<Order> getAllOrder() {
        return orderMapper.getAllOrder();
    }

    @Override
    public Order getOrderById(int id) {
        return orderMapper.getOrderById(id);
    }

    @Override
    public int addNewOrder(Order order) {
        return orderMapper.addNewOrder(order);
    }

    @Override
    public int updateExistingOrder(Order order) {
        return orderMapper.updateExistingOrder(order);
    }

    @Override
    public int deleteExistingOrder(int id) {
        return orderMapper.deleteExistingOrder(id);
    }
    
}
