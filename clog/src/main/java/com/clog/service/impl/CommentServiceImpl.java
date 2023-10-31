package com.clog.service.impl;

import com.clog.entity.Comment;
import com.clog.entity.Post;
import com.clog.exception.ResourceNotFoundException;
import com.clog.payload.CommentDto;
import com.clog.repository.CommentRepository;
import com.clog.repository.PostRepository;
import com.clog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;



    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;

    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id:" + postId)

        );
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);

        CommentDto dto = mapToDTO(newComment);

        return dto;
    }
    private CommentDto mapToDTO(Comment comment){

        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }
    private Comment mapToEntity(CommentDto commentDto){

        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        Post post =postRepository.findById(postId).orElseThrow(

                ()->new ResourceNotFoundException("Postnot found with id:"+postId)
        );


        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());


    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post =postRepository.findById(postId).orElseThrow(

                ()->new ResourceNotFoundException("Postnot found with id:"+postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comment Not Found with id:"+commentId)
        );
        return  mapToDTO(comment);

    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post =postRepository.findById(postId).orElseThrow(

                ()->new ResourceNotFoundException("Postnot found with id:"+postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comment Not Found with id:"+commentId)
        );
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedcomment = commentRepository.save(comment);
        return mapToDTO(updatedcomment);
    }
    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post =postRepository.findById(postId).orElseThrow(

                ()->new ResourceNotFoundException("Post not found with id:"+postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comment Not Found with id:"+commentId)
        );
        commentRepository.deleteById(commentId);
    }

}


