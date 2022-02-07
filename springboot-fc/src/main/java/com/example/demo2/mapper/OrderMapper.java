package com.example.demo2.mapper;

import java.util.ArrayList;

import com.example.demo2.model.Order;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderMapper {
    ArrayList<Order> getAllOrder();
    Order getOrderById(int id);
    int addNewOrder(Order order);
    int updateExistingOrder(Order order);
    int deleteExistingOrder(int id);
}
