package com.pinsoft.intern.service;

import com.pinsoft.intern.dto.LikedDTO;
import com.pinsoft.intern.entity.Blog;
import com.pinsoft.intern.entity.Like;
import com.pinsoft.intern.entity.User;
import com.pinsoft.intern.repository.LikeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final BlogService blogService;

    public List<Like> findAll() {
        return likeRepository.findAll();
    }

    public Like find(int id) {
        Like like = likeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Like not found"));
        return like;
    }

    public Like save(LikedDTO likedDTO) {
        User user = userService.find(likedDTO.getUserId());
        Blog blog = blogService.find(likedDTO.getBlogId());
        Like like = new Like();
        like.setUser(user);
        like.setBlog(blog);
        return likeRepository.save(like);
    }

    public void delete(int id) {
        Like like = find(id);
        likeRepository.delete(like);
    }
}
