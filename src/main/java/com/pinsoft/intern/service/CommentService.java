package com.pinsoft.intern.service;

import com.pinsoft.intern.dto.CommentDTO;
import com.pinsoft.intern.entity.Blog;
import com.pinsoft.intern.entity.Comment;
import com.pinsoft.intern.entity.User;
import com.pinsoft.intern.jwt.CustomUserDetails;
import com.pinsoft.intern.repository.CommentRepository;
import com.pinsoft.intern.validation.CommentValidation;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final BlogService blogService;
    private final CommentValidation commentValidation;

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment find(int id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        return comment;
    }

    public List<Comment> findByUser(int userId) {
        CustomUserDetails userDetails = CustomUserDetailsService.getAuthenticatedUser();

        if (userDetails.isUserSelf(userId) || userDetails.isAdmin()) {
            return commentRepository.findByUser(userId);
        }
        throw new AccessDeniedException("Bu işlem için yetkiniz yok.");
    }

    public List<Comment> findByBlog(int id) {
        List<Comment> comments = commentRepository.findByBlog(id);
        return comments;
    }


    public Comment save(CommentDTO commentDTO) {
        int userId = commentDTO.getUserId();
        int blogId = commentDTO.getBlogId();
        String commentStr = commentDTO.getComment();
        Double rating = commentDTO.getRating();
        User user = userService.find(userId);
        Blog blog = blogService.find(blogId);

        CustomUserDetails userDetails = CustomUserDetailsService.getAuthenticatedUser();


        if (userDetails.isUserSelf(userId)) {
            commentValidation.validation(commentStr, rating);
            Comment comment = new Comment();
            comment.setUser(user);
            comment.setBlog(blog);
            comment.setComment(commentStr);
            comment.setRating(rating);
            return commentRepository.save(comment);
        }
        throw new AccessDeniedException("Bu işlem için yetkiniz yok.");
    }

    public void delete(int commentId) {
        CustomUserDetails userDetails = CustomUserDetailsService.getAuthenticatedUser();

        Comment comment = find(commentId);

        if (userDetails.isUserSelf(comment.getUser().getId()) || userDetails.isAdmin()) {
            commentRepository.delete(comment);
        } else {
            throw new AccessDeniedException("Bu işlem için yetkiniz yok.");
        }
    }
}

