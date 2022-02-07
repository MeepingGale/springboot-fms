package com.example.demo2.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Food {
    public int id;
    public String name;
    public int quantity;
    public int calories;
    public double price;
    public String currency;
    public Date date_created;

    @Override
    public String toString() {
        return "Food Name: " + name + ", Quantity: " + quantity;
    }
}
