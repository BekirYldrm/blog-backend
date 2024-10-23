package com.pinsoft.intern.repository;

import com.pinsoft.intern.entity.Blog;
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
public class BlogRepository implements EntityDao<Blog> {

    private EntityManager entityManager;

    @Override
    @Transactional
    public Blog save(Blog entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Optional<Blog> findById(int id) {
        Blog blog = entityManager.find(Blog.class, id);
        return Optional.ofNullable(blog);
    }

    @Override
    public List<Blog> findAll() {
        return entityManager.createQuery("from Blog").getResultList();
    }

    @Override
    @Transactional
    public Blog update(Blog entity) {
        return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(Blog entity) {
        entityManager.remove(entity);
    }

    public List<Blog> findAllByOrderByPopularityDesc() {
        return entityManager.createQuery("from Blog order by popularity desc ").getResultList();
    }

    public List<Blog> findAllByOrderByTitleAsc() {
        return entityManager.createQuery("from Blog order by title asc ").getResultList();
    }

    public List<Blog> findAllByOrderByDateDesc() {
        return entityManager.createQuery("from Blog order by date desc ").getResultList();
    }
}
