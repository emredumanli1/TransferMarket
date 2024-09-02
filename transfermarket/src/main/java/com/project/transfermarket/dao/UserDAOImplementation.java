package com.project.transfermarket.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.transfermarket.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class UserDAOImplementation implements UserDAO{

    private EntityManager entityManager;

    @Autowired
    public UserDAOImplementation(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findUserByName(String name) {
        TypedQuery<User> theQuery = entityManager.createQuery("select u from User u where u.username = :data ", User.class);
        theQuery.setParameter("data", name);
        User tempUser = theQuery.getSingleResult();
        
        return tempUser;
    }

    @Override
    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void deleteUserByName(String name) {
        TypedQuery<User> theQuery = entityManager.createQuery("select u from User u where u.username = :data ", User.class);
        theQuery.setParameter("data", name);
        User tempUser = theQuery.getSingleResult();
        entityManager.remove(tempUser);
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> theQuery = entityManager.createQuery("from User", User.class);

        List<User> users = theQuery.getResultList();

        return users;
    }

}
