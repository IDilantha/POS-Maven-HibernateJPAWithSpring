package io.github.idilantha.pos.business.custom.impl;




import io.github.idilantha.pos.business.custom.OrderBO;
import io.github.idilantha.pos.dao.custom.*;
import io.github.idilantha.pos.db.JPAUtil;
import io.github.idilantha.pos.dto.OrderDTO;
import io.github.idilantha.pos.dto.OrderDTO2;
import io.github.idilantha.pos.dto.OrderDetailDTO;
import io.github.idilantha.pos.entity.CustomEntity;
import io.github.idilantha.pos.entity.Item;
import io.github.idilantha.pos.entity.Order;
import io.github.idilantha.pos.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class OrderBOImpl implements OrderBO {

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderDetailDAO orderDetailDAO;
    @Autowired
    private ItemDAO itemDAO ;
    @Autowired
    private QueryDAO queryDAO;
    @Autowired
    private CustomerDAO customerDAO;


    @Override
    public int getLastOrderId() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        orderDAO.setEntityManager(entityManager);
        entityManager.getTransaction().begin();

        int lastOrderId = orderDAO.getLastOrderId();
        entityManager.getTransaction().commit();
        entityManager.close();
        return lastOrderId;
    }

    @Override
    public void placeOrder(OrderDTO order) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        orderDAO.setEntityManager(entityManager);
        orderDetailDAO.setEntityManager(entityManager);
        customerDAO.setEntityManager(entityManager);
        itemDAO.setEntityManager(entityManager);
        entityManager.getTransaction().begin();

        int oId = order.getId();
        orderDAO.save(new Order(oId, new java.sql.Date(new Date().getTime()), customerDAO.find(order.getCustomerId())));

        for (OrderDetailDTO orderDetail : order.getOrderDetails()) {
            orderDetailDAO.save(new OrderDetail(oId, orderDetail.getCode(), orderDetail.getQty(), orderDetail.getUnitPrice()));

            Item item = itemDAO.find(orderDetail.getCode());
            item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
            itemDAO.update(item);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<OrderDTO2> getOrderInfo() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        queryDAO.setEntityManager(entityManager);
        entityManager.getTransaction().begin();

        List<CustomEntity> ordersInfo = queryDAO.getOrdersInfo();
        List<OrderDTO2> dtos = new ArrayList<>();
        for (CustomEntity info : ordersInfo) {
            dtos.add(new OrderDTO2(info.getOrderId(), info.getOrderDate(), info.getCustomerId(), info.getCustomerName(), info.getOrderTotal()));
        }
        entityManager.getTransaction().commit();
        return dtos;
    }
}
