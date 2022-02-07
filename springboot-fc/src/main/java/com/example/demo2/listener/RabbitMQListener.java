package com.example.demo2.listener;

import java.util.ArrayList;

import com.example.demo2.configuration.RabbitMQConfig;
import com.example.demo2.model.Food;
import com.example.demo2.model.Order;
import com.example.demo2.service.FoodService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @Autowired
    private FoodService foodService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void listener(Order order) {
		System.out.println("+-------------------------------------------------+");
        System.out.println("Message: " + order);

		ArrayList<Food> list = foodService.getAllFood();

		for (Food food : list) {
			if(order.name.equals(food.name)) {
				System.out.println("Food before: " + food.toString());
				food.quantity -= order.quantity;

				if(food.quantity > 0) food.quantity = 0;

				System.out.println("Food after: " + food.toString());
				foodService.updateExistingFood(food);
				return;
			}
		}
    }
}
