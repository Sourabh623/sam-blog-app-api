package com.samtechblog.controllers;

import com.samtechblog.payloads.APIResponse;
import com.samtechblog.payloads.CommentDto;
import com.samtechblog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId){
        CommentDto saveCommentDto = this.commentService.createComment(commentDto,postId);
        return new ResponseEntity<>(saveCommentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<APIResponse> deleteComment(@PathVariable("commentId") Integer commentId)
    {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<APIResponse>(new APIResponse("Comment is Deleted Successfully",true),HttpStatus.OK);
    }
}
