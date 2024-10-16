package com.pinsoft.intern.repository;


import com.pinsoft.intern.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Data
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserRepository implements EntityDao<User> {

    private EntityManager entityManager;

    @Override
    @Transactional
    public User save(User entity) {
         entityManager.persist(entity);
         return entity;
    }

    @Override
    public Optional<User> findById(int id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("from User").getResultList();
    }

    public Optional<User> findByEmail(String email) {
        try{
            TypedQuery<User> query = entityManager.createQuery("from User where email = :email", User.class);
            User foundUser = query.setParameter("email", email).getSingleResult();
            return Optional.of(foundUser);
        }
        catch (NoResultException e){
            return Optional.empty();
        }

    }

    @Override
    @Transactional
    public User update(User entity) {
       return  entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(User entity) {
        entityManager.remove(entity);
    }

    public Optional<User> findByUsername(String username) {
        try {
            TypedQuery<User> query = entityManager.createQuery(" from User where username = :username", User.class);
            User foundUser = query.setParameter("username", username).getSingleResult();
            return Optional.of(foundUser);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


}
