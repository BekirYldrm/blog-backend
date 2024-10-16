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
import java.util.Optional;


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

    public List<User> findUsers(int blogId) {
        Blog blog = blogService.find(blogId);
        return likeRepository.findUsers(blog);
    }

    public List<Blog> findBlogs(int userId) {
        User user = userService.find(userId);
        return likeRepository.findBlogs(user);
    }

    public Like save(LikedDTO likedDTO) {

        int userId = likedDTO.getUserId();
        int blogId = likedDTO.getBlogId();
        User user = userService.find(userId);
        Blog blog = blogService.find(blogId);

        Optional<Like> likeOptional = likeRepository.findByUserAndBlog(userId, blogId);

        if (likeOptional.isPresent()) {
            delete(likeOptional.get().getId().intValue());
            blogService.decreasePopularity(blog);
            return null;
        } else {
            Like like = new Like();
            like.setUser(user);
            blogService.increasePopularity(blog);
            like.setBlog(blog);
            return likeRepository.save(like);
        }

    }

    public void delete(int id) {
        Like like = find(id);
        likeRepository.delete(like);
    }


}
