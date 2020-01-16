package io.github.idilantha.pos.dao;



import io.github.idilantha.pos.entity.SuperEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class CrudDAOImpl<T extends SuperEntity,Id extends Serializable> implements CrudDAO<T,Id> {

    @PersistenceContext
    protected EntityManager entityManager;
    private Class<T> entity;

    public CrudDAOImpl() {
        entity = (Class<T>)((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
    }

    @Override
    public List<T> findAll() throws Exception {
        return entityManager.createQuery("FROM "+entity.getName()).getResultList();
    }

    @Override
    public T find(Id id) throws Exception {
        return entityManager.find(entity,id);
    }

    @Override
    public void save(T entity) throws Exception {
         entityManager.persist(entity);
    }

    @Override
    public void update(T entity) throws Exception {
        entityManager.merge(entity);
    }

    @Override
    public void delete(Id id) throws Exception {
        entityManager.remove(entityManager.getReference(entity,id));
    }
}
