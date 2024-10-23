package com.pinsoft.intern.repository;

import com.pinsoft.intern.entity.Category;
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
public class CategoryRepository implements EntityDao<Category> {

    private EntityManager entityManager;

    @Override
    @Transactional
    public Category save(Category entity) {
         entityManager.persist(entity);
         return entity;
    }

    @Override
    public Optional<Category> findById(int id) {
        Category category = entityManager.find(Category.class, id);
        return Optional.ofNullable(category);
    }

    @Override
    public List<Category> findAll() {
        return entityManager.createQuery("from Category").getResultList();
    }

    @Override
    @Transactional
    public Category update(Category entity) {
       return  entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(Category entity) {
        entityManager.remove(entity);
    }
}
