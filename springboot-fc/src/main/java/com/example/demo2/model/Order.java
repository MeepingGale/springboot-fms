package com.example.demo2.model;

import java.util.Date;

import lombok.Data;

@Data
public class Order {
    public int id;
    public String name;
    public int quantity;
    public Date date_created;
}
