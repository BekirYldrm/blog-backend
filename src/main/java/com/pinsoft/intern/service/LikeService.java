package com.pinsoft.intern.service;

import com.pinsoft.intern.dto.LikedDTO;
import com.pinsoft.intern.entity.Blog;
import com.pinsoft.intern.entity.Like;
import com.pinsoft.intern.entity.User;
import com.pinsoft.intern.jwt.CustomUserDetails;
import com.pinsoft.intern.repository.LikeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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

        CustomUserDetails userDetails = CustomUserDetailsService.getAuthenticatedUser();
        Blog blog = blogService.find(blogId);
        int authorId = blog.getAuthor().getId();

        if (userDetails.isAuthorSelf(authorId) || userDetails.isAdmin()) {
            return likeRepository.findUsers(blog);
        }
        throw new AccessDeniedException("Bu işlem için yetkiniz yok.");
    }

    public List<Blog> findBlogs(int userId) {

        CustomUserDetails userDetails = CustomUserDetailsService.getAuthenticatedUser();
        User user = userService.find(userId);

        if (userDetails.isUserSelf(userId) || userDetails.isAdmin()) {
            return likeRepository.findBlogs(user);
        }
        throw new AccessDeniedException("Bu işlem için yetkiniz yok.");
    }

    public void save(LikedDTO likedDTO) {

        int userId = likedDTO.getUserId();
        int blogId = likedDTO.getBlogId();
        User user = userService.find(userId);
        Blog blog = blogService.find(blogId);
        Optional<Like> likeOptional = likeRepository.findByUserAndBlog(userId, blogId);

        CustomUserDetails userDetails = CustomUserDetailsService.getAuthenticatedUser();

        if (userDetails.getId() == userId) {

            if (likeOptional.isPresent()) {
                delete(likeOptional.get().getId().intValue());
                blogService.decreasePopularity(blog);
            } else {
                Like like = new Like();
                like.setUser(user);
                blogService.increasePopularity(blog);
                like.setBlog(blog);
                likeRepository.save(like);
            }
        } else {
            throw new AccessDeniedException("Bu işlem için yetkiniz yok.");

        }
    }

    private void delete(int id) {
        Like like = find(id);
        likeRepository.delete(like);
    }


}
