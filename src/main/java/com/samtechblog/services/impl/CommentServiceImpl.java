package com.samtechblog.services.impl;

import com.samtechblog.exceptions.ResourceNotFoundException;
import com.samtechblog.models.Comment;
import com.samtechblog.models.Post;
import com.samtechblog.payloads.CommentDto;
import com.samtechblog.repositories.CommentRepository;
import com.samtechblog.repositories.PostRepository;
import com.samtechblog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        //getting the post first
        Post post = this.postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment saveComment = this.commentRepository.save(comment);

        return this.modelMapper.map(saveComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        //getting the comment by commentId from db
        Comment comment = this.commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Comment","Id", commentId));

        //delete the comment
        this.commentRepository.delete(comment);

    }
}
