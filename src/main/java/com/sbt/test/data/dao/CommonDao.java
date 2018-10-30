package com.sbt.test.data.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CommonDao<T> {

    private static final Logger log = LoggerFactory.getLogger(CommonDao.class);

    private SessionFactory sessionFactory;
    private String entityHql;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(T object){
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(object);
        tx.commit();
        session.close();
    }

    public List<T> getList() {
        Session session = this.sessionFactory.openSession();
        List<T> objectsList = session.createQuery(entityHql).list();
        session.close();
        return objectsList;
    }

}
