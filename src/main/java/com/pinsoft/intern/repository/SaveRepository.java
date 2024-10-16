package com.pinsoft.intern.repository;

import com.pinsoft.intern.entity.Blog;
import com.pinsoft.intern.entity.Save;
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
public class SaveRepository implements EntityDao<Save> {

    private EntityManager entityManager;

    @Override
    @Transactional
    public Save save(Save entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Optional<Save> findById(int id) {
        Save save = entityManager.find(Save.class, id);
        return Optional.ofNullable(save);
    }

    @Override
    public List<Save> findAll() {
        return entityManager.createQuery("from Save ").getResultList();
    }

    @Override
    @Transactional
    public Save update(Save entity) {
        return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(Save entity) {
        entityManager.remove(entity);
    }

    public Optional<Save> findByUserAndBlog(int userId, int blogId) {

        try {
            TypedQuery<Save> query = entityManager.createQuery("from Save where user.id= :userId and blog.id = :blogId", Save.class);
            query.setParameter("userId", userId);
            query.setParameter("blogId", blogId);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<Blog> findBlogs(User user) {
        int userId = user.getId();
        TypedQuery<Blog> query = entityManager.createQuery("select l.blog from Save l where l.user.id= :userId", Blog.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
