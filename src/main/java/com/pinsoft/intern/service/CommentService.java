package com.pinsoft.intern.service;

import com.pinsoft.intern.dto.CommentDTO;
import com.pinsoft.intern.entity.Blog;
import com.pinsoft.intern.entity.Comment;
import com.pinsoft.intern.entity.User;
import com.pinsoft.intern.repository.CommentRepository;
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

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment find(int id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        return comment;
    }

    public Comment save(CommentDTO commentDTO) {
        User user = userService.find(commentDTO.getUserId());
        Blog blog = blogService.find(commentDTO.getBlogId());
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setBlog(blog);
        comment.setComment(commentDTO.getComment());
        comment.setRating(commentDTO.getRating());
        return commentRepository.save(comment);
    }

    public void delete(int id) {
        Comment comment = find(id);
        commentRepository.delete(comment);
    }


}
