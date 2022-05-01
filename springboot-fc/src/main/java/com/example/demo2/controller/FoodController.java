package com.example.demo2.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

import com.example.demo2.model.Food;
import com.example.demo2.service.FoodService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class FoodController {

    @Autowired
    FoodService foodService;

    private final static String QUEUE_NAME = "food_queue";

    // private void callMQ(String message) throws Exception {
    //     ConnectionFactory factory = new ConnectionFactory();
    //     factory.setHost("localhost");
    //     try (Connection connection = factory.newConnection();
    //          Channel channel = connection.createChannel()) {
    //         channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    //         channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
    //         System.out.println(" [PRODUCER] Sent '" + message + "'");
    //     }
    // }

    @GetMapping("/foods")
    public ArrayList<Food> foods() {
        // try {
        //     callMQ("List of food has been displayed.");
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        return foodService.getAllFood();
    }

    @GetMapping("/food/{id}")
    public Food food(@PathVariable int id) {
        // try {
        //     callMQ("Food with id (" + id + ") has been displayed.");
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        return foodService.getFoodById(id);
    }

    @PostMapping("/addFood")
    public String addFood(@RequestBody Food food) {
        food.date_created = new Date();
        if (food.date_created == null || food.calories == 0 || food.currency == "" || food.name == ""
                || food.price == 0)
            return "One or more fields is/are empty, please have all of the fields filled up.";

        foodService.addNewFood(food);

        // try {
        //     callMQ("A new food has been added.");
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        return "Successfully added into the database.";
    }

    @PutMapping("/updateFood")
    public String updateFood(@RequestBody Food food) {
        int numUpdated = foodService.updateExistingFood(food);

        // try {
        //     callMQ("Food with id (" + food.id + ") has been updated.");
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        return numUpdated > 0 ? "Update successful." : "Update failed. Check if your id is correct.";
    }

    @DeleteMapping("/deleteFood/{id}")
    public String deleteFood(@PathVariable int id) {
        int numDeleted = foodService.deleteExistingFood(id);

        // try {
        //     callMQ("Food with id (" + id + ") has been deleted.");
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        return numDeleted > 0 ? "Successfully deleted id " + id + "." : "Id doesn't exist.";
    }
}
