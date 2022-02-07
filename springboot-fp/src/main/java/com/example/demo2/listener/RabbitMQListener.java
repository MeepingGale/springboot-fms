package com.example.demo2.listener;

import java.util.ArrayList;

import com.example.demo2.configuration.RabbitMQConfig;
import com.example.demo2.model.Food;
import com.example.demo2.service.FoodService;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {
    // protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FoodService foodService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void listener(String message) {
        // logger.info("Listening...");
        System.out.println("Listening...");
        System.out.println("Message: " + message);

        try {

        } catch (Exception e) {
           
        }
    }

    public void process(String message) {
		if (message.charAt(0) == '[' && message.charAt(message.length() - 1) == ')') {
			String name = "";
			String quantity = "";
			boolean isQuantity = false;

			for (int i = 1; i < message.length() - 1; i++) {
				if(message.charAt(i) == ']') {
					isQuantity = true;
					i += 2;
					continue;
				}
				if(isQuantity) {
					quantity += message.charAt(i);
				} else {
					name += message.charAt(i);
				}
			}

			System.out.println("Name is " + name);
			System.out.println("Quantity is " + quantity);

			Food food = new Food();
			ArrayList<Food> foods = foodService.getAllFood();

			for (Food f : foods) {
				if(f.name.equals(name)) {
					food = f;
					food.quantity = food.quantity - Integer.parseInt(quantity);
				}
			}

			System.out.println("Updated qty is " + food.quantity);

			foodService.updateExistingFood(food);
		}
    }
}
