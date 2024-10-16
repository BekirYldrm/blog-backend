package com.pinsoft.intern.repository;

import com.pinsoft.intern.entity.Like;
import jakarta.persistence.EntityManager;
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
public class LikeRepository implements EntityDao<Like> {

    private EntityManager entityManager;

    @Override
    @Transactional
    public Like save(Like entity) {
         entityManager.persist(entity);
         return entity;
    }

    @Override
    public Optional<Like> findById(int id) {
        Like like = entityManager.find(Like.class, id);
        return Optional.ofNullable(like);
    }

    @Override
    public List<Like> findAll() {
        return entityManager.createQuery("from Favorite ").getResultList();
    }

    @Override
    @Transactional
    public Like update(Like entity) {
       return  entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(Like entity) {
        entityManager.remove(entity);
    }
}
