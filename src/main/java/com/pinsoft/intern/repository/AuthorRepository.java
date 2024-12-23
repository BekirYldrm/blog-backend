package com.pinsoft.intern.repository;

import com.pinsoft.intern.entity.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
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
public class AuthorRepository implements EntityDao<Author> {

    private EntityManager entityManager;

    @Autowired
    public AuthorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Author save(Author entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Optional<Author> findById(int id) {
        Author author = entityManager.find(Author.class, id);
        return Optional.ofNullable(author);
    }

    @Override
    public List<Author> findAll() {
        return entityManager.createQuery("from Author").getResultList();
    }

    public Optional<Author> findByEmail(String email) {
        try {
            TypedQuery<Author> query = entityManager.createQuery("from Author where email = :email", Author.class);
            Author foundAuthor = query.setParameter("email", email).getSingleResult();
            return Optional.of(foundAuthor);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Author update(Author entity) {
        return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(Author entity) {
        entityManager.remove(entity);
    }

    public Author findByEmailAndPassword(String email, String password) {
        TypedQuery<Author> query = entityManager.createQuery("SELECT a FROM Author a WHERE a.email = :email AND a.password = :password", Author.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.getResultStream().findFirst().orElse(null);
    }

    public Author findByBlog(int blogId) {
        String jpql = "select a from Author a join a.myBlogs b where b.id = :blogId";
        TypedQuery<Author> query = entityManager.createQuery(jpql, Author.class);
        query.setParameter("blogId", blogId);
        return (Author) query.getSingleResult();
    }
}
