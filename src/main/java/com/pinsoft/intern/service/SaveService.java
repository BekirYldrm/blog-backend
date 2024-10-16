package com.pinsoft.intern.service;

import com.pinsoft.intern.dto.SaveDTO;
import com.pinsoft.intern.entity.Blog;
import com.pinsoft.intern.entity.Save;
import com.pinsoft.intern.entity.User;
import com.pinsoft.intern.repository.SaveRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
public class SaveService {

    private final SaveRepository saveRepository;
    private final UserService userService;
    private final BlogService blogService;

    public List<Save> findAll() {
        return saveRepository.findAll();
    }

    public Save find(int id) {
        Save save = saveRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Save not found"));
        return save;
    }

    public List<Blog> findBlogs(int userId) {
        User user = userService.find(userId);
        return saveRepository.findBlogs(user);
    }

    public Save save(SaveDTO saveDTO) {

        int userId = saveDTO.getUserId();
        int blogId = saveDTO.getBlogId();
        User user = userService.find(userId);
        Blog blog = blogService.find(blogId);

        Optional<Save> saveOptional = saveRepository.findByUserAndBlog(userId, blogId);

        if (saveOptional.isPresent()) {
            delete(saveOptional.get().getId().intValue());
            return null;
        } else {
            Save save = new Save();
            save.setUser(user);
            save.setBlog(blog);
            return saveRepository.save(save);
        }
    }

    public void delete(int id) {
        Save save = find(id);
        saveRepository.delete(save);
    }
}
