package com.labus.bunkerpub.dao;

import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
public class Check {
    private int id;
    private int userId;
    private Table table;
    private Date date;
    private LocalTime localtime;
    private Dishes dishes;
    private String room;
}
