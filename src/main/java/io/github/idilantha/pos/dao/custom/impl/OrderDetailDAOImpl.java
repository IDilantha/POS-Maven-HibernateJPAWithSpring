package io.github.idilantha.pos.dao.custom.impl;


import io.github.idilantha.pos.dao.CrudDAOImpl;
import io.github.idilantha.pos.dao.custom.OrderDetailDAO;
import io.github.idilantha.pos.entity.OrderDetail;
import io.github.idilantha.pos.entity.OrderDetailPK;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class OrderDetailDAOImpl extends CrudDAOImpl<OrderDetail, OrderDetailPK> implements OrderDetailDAO {

    @Override
    public boolean existsByItemCode(String itemCode) throws Exception {
        Query nativeQuery = entityManager.createNativeQuery("SELECT * FROM OrderDetail WHERE itemCode=? LIMIT 1");
        nativeQuery.setParameter(1,itemCode);
        return !nativeQuery.getResultList().isEmpty() ;
    }
}
