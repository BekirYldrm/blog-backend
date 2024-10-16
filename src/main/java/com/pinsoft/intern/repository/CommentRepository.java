package com.pinsoft.intern.repository;

import com.pinsoft.intern.entity.Comment;
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
public class CommentRepository implements EntityDao<Comment> {

    private EntityManager entityManager;


    @Override
    @Transactional
    public Comment save(Comment entity) {
         entityManager.persist(entity);
         return entity;
    }


    @Override
    public Optional<Comment> findById(int id) {
        Comment comment = entityManager.find(Comment.class, id);
        return Optional.ofNullable(comment);
    }

    @Override
    public List<Comment> findAll() {
        return entityManager.createQuery("from Comment").getResultList();
    }

    @Override
    @Transactional
    public Comment update(Comment entity) {
       return  entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(Comment entity) {
        entityManager.remove(entity);
    }
}
