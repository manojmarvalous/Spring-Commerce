package com.techouts.repositoryimpl;



import com.techouts.model.Orders;
import com.techouts.model.User;
import com.techouts.repository.OrdersRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrdersRepoImpl implements OrdersRepo {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void saveOrder(Orders order) {
        getSession().save(order);
    }

    @Override
    public Orders findById(int id) {
        return getSession().get(Orders.class, id);
    }

    @Override
    public List<Orders> findByUserId(long userId) {
        Query<Orders> query = getSession().createQuery(
                "FROM Orders o WHERE o.user.id = :userId", Orders.class
        );
        query.setParameter("userId", userId);
        return query.list();
    }

    @Override
    public List<Orders> findAllOrders() {
        Query<Orders> query = getSession().createQuery("FROM Orders", Orders.class);
        return query.list();
    }

    @Override
    public int getOrderCount(User user) {

        Long count = getSession().createQuery(
                        "SELECT COUNT(o) FROM Orders o WHERE o.user = :user",
                        Long.class
                )
                .setParameter("user", user)
                .uniqueResult();

        return count != null ? count.intValue() : 0;
    }
}