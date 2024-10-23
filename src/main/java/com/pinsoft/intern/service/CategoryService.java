package com.pinsoft.intern.service;

import com.pinsoft.intern.dto.CategoryDTO;
import com.pinsoft.intern.entity.Category;
import com.pinsoft.intern.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category find(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return category;
    }

    public Category save(CategoryDTO categoryDTO) {
        String categoryName = categoryDTO.getCategoryName();

        if (categoryName == null || categoryName.isEmpty()) {
            throw new EntityNotFoundException("Category name not found");
        }

        Category category = new Category();
        category.setCategoryName(categoryName);
        return categoryRepository.save(category);
    }

    public void delete(int id) {
        Category category = find(id);
        categoryRepository.delete(category);
    }

}
