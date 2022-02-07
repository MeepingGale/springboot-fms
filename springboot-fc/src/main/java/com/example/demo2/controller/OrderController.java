package com.example.demo2.controller;

import java.nio.charset.StandardCharsets;
import java.util.*;

import com.example.demo2.model.Order;
import com.example.demo2.service.OrderService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    private final static String QUEUE_NAME = "food_order_queue";

    private void callMQ(String message) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [PRODUCER] Sent '" + message + "'");
        }
    }

    @GetMapping("/orders")
    public ArrayList<Order> orders() {
        try {
            callMQ("List of orders has been displayed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderService.getAllOrder();
    }

    @GetMapping("/order/{id}")
    public Order order(@PathVariable int id) {
        try {
            callMQ("Food with id (" + id + ") has been displayed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderService.getOrderById(id);
    }

    @PostMapping("/addOrder")
    public String addOrder(@RequestBody Order order) {
        order.date_created = new Date();
        if (order.date_created == null || order.quantity == 0 || order.name.equals(""))
            return "One or more fields is/are empty, please have all of the fields filled up.";

        orderService.addNewOrder(order);

        try {
            callMQ("[" + order.name + "] (" + order.quantity + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Successfully added into the database.";
    }

    @PutMapping("/updateOrder")
    public String updateOrder(@RequestBody Order order) {
        int numUpdated = orderService.updateExistingOrder(order);

        try {
            callMQ("Order with id (" + order.id + ") has been updated.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return numUpdated > 0 ? "Update successful." : "Update failed. Check if your id is correct.";
    }

    @DeleteMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable int id) {
        int numDeleted = orderService.deleteExistingOrder(id);

        try {
            callMQ("Order with id (" + id + ") has been deleted.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return numDeleted > 0 ? "Successfully deleted id " + id + "." : "Id doesn't exist.";
    }
    
}
