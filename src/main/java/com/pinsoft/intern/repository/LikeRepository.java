package com.pinsoft.intern.repository;

import com.pinsoft.intern.entity.Blog;
import com.pinsoft.intern.entity.Like;
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
        return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(Like entity) {
        entityManager.remove(entity);
    }

    public Optional<Like> findByUserAndBlog(int userId, int blogId) {

        try {
            TypedQuery<Like> query = entityManager.createQuery("from Favorite where user.id= :userId and blog.id = :blogId", Like.class);
            query.setParameter("userId", userId);
            query.setParameter("blogId", blogId);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<User> findUsers(Blog blog) {
        int blogId = blog.getId();
        TypedQuery<User> query = entityManager.createQuery("select l.user from Favorite l where l.blog.id = :blogId", User.class);
        query.setParameter("blogId", blogId);
        return query.getResultList();
    }

    public List<Blog> findBlogs(User user) {
        int userId = user.getId();
        TypedQuery<Blog> query = entityManager.createQuery("select l.blog from Favorite l where l.user.id= :userId", Blog.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
