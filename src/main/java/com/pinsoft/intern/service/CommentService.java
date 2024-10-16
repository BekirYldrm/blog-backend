package com.pinsoft.intern.service;

import com.pinsoft.intern.dto.CommentDTO;
import com.pinsoft.intern.entity.Blog;
import com.pinsoft.intern.entity.Comment;
import com.pinsoft.intern.entity.User;
import com.pinsoft.intern.repository.CommentRepository;
import com.pinsoft.intern.validation.CommentValidation;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Comment> findByUser(int id) {
        List<Comment> comments = commentRepository.findByUser(id);
        return comments;
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

        commentValidation.validation(commentStr, rating);

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setBlog(blog);
        comment.setComment(commentStr);
        comment.setRating(rating);
        return commentRepository.save(comment);
    }

    public void delete(int id) {
        Comment comment = find(id);
        commentRepository.delete(comment);
    }


}
