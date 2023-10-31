package com.clog.service;

import com.clog.payload.PostDto;

import java.util.List;

public interface PostService {



    PostDto createPost(PostDto postDto);

    List<PostDto> listAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(long id);

    PostDto updatepost(long id, PostDto postDto);


    void deletePostById(long id);



}
