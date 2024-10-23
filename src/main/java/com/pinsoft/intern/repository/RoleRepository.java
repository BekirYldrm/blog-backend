package com.pinsoft.intern.repository;

import com.pinsoft.intern.entity.Role;
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
public class RoleRepository implements EntityDao<Role> {

    private EntityManager entityManager;

    @Override
    @Transactional
    public Role save(Role entity) {
         entityManager.persist(entity);
         return entity;
    }

    @Override
    public Optional<Role> findById(int id) {
        Role role = entityManager.find(Role.class, id);
        return Optional.ofNullable(role);
    }

    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("from Role").getResultList();
    }

    @Override
    @Transactional
    public Role update(Role entity) {
       return  entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(Role entity) {
        entityManager.remove(entity);
    }
}
