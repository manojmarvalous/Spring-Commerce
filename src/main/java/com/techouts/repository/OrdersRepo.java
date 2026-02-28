package com.techouts.repository;


import com.techouts.model.Orders;
import com.techouts.model.User;

import java.util.List;

public interface OrdersRepo {

    void saveOrder(Orders order);

    Orders findById(int id);

    List<Orders> findByUserId(long userId);

    List<Orders> findAllOrders();

    int getOrderCount(User user);
}