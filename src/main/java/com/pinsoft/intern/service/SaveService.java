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

    public Save save(SaveDTO likedDTO) {
        User user = userService.find(likedDTO.getUserId());
        Blog blog = blogService.find(likedDTO.getBlogId());
        Save save = new Save();
        save.setUser(user);
        save.setBlog(blog);
        return saveRepository.save(save);
    }

    public void delete(int id) {
        Save save = find(id);
        saveRepository.delete(save);
    }
}
