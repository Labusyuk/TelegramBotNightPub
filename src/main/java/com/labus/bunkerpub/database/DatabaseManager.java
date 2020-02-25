package com.labus.bunkerpub.database;

import com.labus.bunkerpub.dao.Check;
import com.labus.bunkerpub.dao.Dishes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    static Map<Integer, Check> checkMap = new HashMap<>();
    static public Check getCheck(int userId){
        return checkMap.get(userId);
    }
    static public void setCheck(Check check){
        checkMap.put(check.getUserId(),check);
    }
}
