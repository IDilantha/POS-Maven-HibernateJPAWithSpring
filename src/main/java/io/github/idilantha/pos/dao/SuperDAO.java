package io.github.idilantha.pos.dao;


import javax.persistence.EntityManager;

public interface SuperDAO {

    void setEntityManager(EntityManager entityManager);

}
