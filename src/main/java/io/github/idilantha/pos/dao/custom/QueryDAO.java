package io.github.idilantha.pos.dao.custom;


import io.github.idilantha.pos.dao.SuperDAO;
import io.github.idilantha.pos.entity.CustomEntity;

import java.util.List;

public interface QueryDAO extends SuperDAO {



    /**
     *
     * @param query customerId, customerName, orderId, orderDate
     * @return
     * @throws Exception
     */
    List<CustomEntity> getOrdersInfo() throws Exception;

}
