package com.example.demo2.controller;

import java.util.*;

import com.example.demo2.config.RabbitMQConfig;
import com.example.demo2.model.Order;
import com.example.demo2.service.OrderService;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    private RabbitTemplate template;

    @GetMapping("/orders")
    public ArrayList<Order> orders() {
        return orderService.getAllOrder();
    }

    @GetMapping("/order/{id}")
    public Order order(@PathVariable int id) {
        return orderService.getOrderById(id);
    }

    @PostMapping("/addOrder")
    public String addOrder(@RequestBody Order order) {
        order.date_created = new Date();
        if (order.date_created == null || order.quantity == 0 || order.name.equals(""))
            return "One or more fields is/are empty, please have all of the fields filled up.";

        orderService.addNewOrder(order);

        template.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, order);

        return "Successfully added into the database.";
    }

    @PutMapping("/updateOrder")
    public String updateOrder(@RequestBody Order order) {
        int numUpdated = orderService.updateExistingOrder(order);
        return numUpdated > 0 ? "Update successful." : "Update failed. Check if your id is correct.";
    }

    @DeleteMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable int id) {
        int numDeleted = orderService.deleteExistingOrder(id);
        return numDeleted > 0 ? "Successfully deleted id " + id + "." : "Id doesn't exist.";
    }
    
}
