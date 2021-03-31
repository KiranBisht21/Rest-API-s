package Service;

import pojo.Order;

import java.util.ArrayList;
import java.util.List;

public class CustomOperations {
    public static List<Order> createDummyList(){
        List<Order> orderList = new ArrayList<>();

        orderList.add(new Order(1,"Laptop",1));
        orderList.add(new Order(2,"Mobile",2));
        return orderList;
    }
}
