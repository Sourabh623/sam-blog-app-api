package com.samtechblog.services.impl;

import com.samtechblog.exceptions.ResourceNotFoundException;
import com.samtechblog.exceptions.UserNotFoundException;
import com.samtechblog.models.Category;
import com.samtechblog.models.Post;
import com.samtechblog.models.User;
import com.samtechblog.payloads.PostDto;
import com.samtechblog.payloads.PostResponse;
import com.samtechblog.repositories.CategoryRepository;
import com.samtechblog.repositories.PostRepository;
import com.samtechblog.repositories.UserRepository;
import com.samtechblog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        //get the user
        User user = this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",userId));

        //get the category
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));

        //PostDto convert to post
        Post post = this.dtoToPost(postDto);

        //set the post data
        post.setCategory(category);
        post.setUser(user);
        post.setPostCreatedAt(new Date());
        post.setPostUpdatedAt(new Date());

        //save post in the db
        Post savePost = this.postRepository.save(post);
        return this.postToPostDto(savePost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        //getting the post first
        Post post = this.postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));

        //set the data second in post
        post.setPostTitle(postDto.getPostTitle());
        post.setPostContent(postDto.getPostContent());
        post.setPostImageName(postDto.getPostImageName());
        post.setPostUpdatedAt(new Date());

        //save updated post in db
        Post updatedPost = this.postRepository.save(post);

        return this.postToPostDto(updatedPost);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        this.postRepository.delete(post);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));

        return this.postToPostDto(post); // or this.modelMapper.map(post, PostDto.class)
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort =((sortDir.toLowerCase(Locale.ROOT).equals("des"))?Sort.by(sortBy).descending():Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> pagePost = this.postRepository.findAll(pageable);
        List<Post> allPost = pagePost.getContent();

        List<PostDto>  postDtoList = allPost.stream().map(this::postToPostDto).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElement(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        //get the user
        User user = this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",userId));

        //get the post by the user
        List<Post> userPostsList = this.postRepository.findByUser(user);

        //return user post
        return userPostsList.stream().map(this::postToPostDto).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        //get the category by category id
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));
        //get the all post by category
        List<Post> postList = this.postRepository.findByCategory(category);
        return postList.stream().map(this::postToPostDto).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        
        List<Post> posList = this.postRepository.findByPostTitleContaining(keyword);
        List<PostDto> postDtoList = posList.stream().map(this::postToPostDto).collect(Collectors.toList());
        return postDtoList;
    }

    //using model mapper lib to map postDto to post conversion here
    public Post dtoToPost(PostDto postDto){
        return this.modelMapper.map(postDto, Post.class);  //map(source, destination)
    }

    //using model mapper lib to map user to userDto conversion here
    public PostDto postToPostDto(Post post){
        return this.modelMapper.map(post, PostDto.class);  //map(source, destination)
    }

}
