package com.clog.controller;

import com.clog.payload.CommentDto;
import com.clog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    //http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                    @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }
    //http://localhost:8080/api/posts/4/comments

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId")
                                                Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    //http://localhost:8080/api/posts/4/comments/2
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value =
            "postId") Long postId,
                                                     @PathVariable(value = "id") Long commentId) {
        CommentDto commentDto = commentService.getCommentById(postId,
                commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/{postId}/comments{id}
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(

            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "id") Long commentId,
            @RequestBody CommentDto commentDto) {
        CommentDto dto = commentService.updateComment(postId,
                commentId, commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/{postId}/comments{id}
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "id") Long commentId){

        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment is deleted",HttpStatus.OK);
    }
}