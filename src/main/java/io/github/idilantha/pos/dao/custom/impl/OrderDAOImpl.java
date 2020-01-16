package io.github.idilantha.pos.dao.custom.impl;


import io.github.idilantha.pos.dao.CrudDAOImpl;
import io.github.idilantha.pos.dao.custom.OrderDAO;
import io.github.idilantha.pos.entity.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class OrderDAOImpl extends CrudDAOImpl<Order,Integer> implements OrderDAO {

    @Override
    public int getLastOrderId() throws Exception {
        Object o = entityManager.createNativeQuery("SELECT id FROM `Order` ORDER BY id DESC LIMIT 1").getSingleResult();
        return ( o == null ) ? 0 : (int)o;
    }

    @Override
    public boolean existsByCustomerId(String customerId) throws Exception {
        Query nativeQuery = entityManager.createNativeQuery("SELECT * FROM `Order` WHERE customerId=? LIMIT 1");
        nativeQuery.setParameter(1,customerId);
        return !nativeQuery.getResultList().isEmpty();
    }

}
