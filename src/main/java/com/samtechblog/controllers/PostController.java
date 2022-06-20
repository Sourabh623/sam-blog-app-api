package com.samtechblog.controllers;

import com.samtechblog.configurations.AppConstants;
import com.samtechblog.payloads.APIResponse;
import com.samtechblog.payloads.PostDto;
import com.samtechblog.payloads.PostResponse;
import com.samtechblog.services.FileService;
import com.samtechblog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    //application.properties key mention
    @Value("${project.image}")
    String path;

    //create the post
    @PostMapping("/user/{userId}/category/{categoryId}/post")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,@PathVariable Integer categoryId){
        PostDto createdPost = this.postService.createPost(postDto,userId, categoryId);
        return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
    }

    //get all posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_ASC, required = false) String sortDir
    ){
        PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    //get the single post
    //get all posts
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Integer postId){
        return ResponseEntity.ok(this.postService.getPostById(postId));
    }

    //get post by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId)
    {
        List<PostDto> postDtoList = this.postService.getPostByUser(userId);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    //get post by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId)
    {
        List<PostDto> postDtoList = this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    //delete post by id
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<APIResponse> deletePost(@PathVariable Integer postId)
    {
        this.postService.deletePost(postId);
        return new ResponseEntity<APIResponse>(new APIResponse("Post is Deleted SuccessFully",true),HttpStatus.OK);
    }

    //update post by id
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId)
    {
        PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.OK);
    }

    //get all posts
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPosts(@PathVariable(value = "keywords") String keywords){
        List<PostDto> postDtoList = this.postService.searchPost(keywords);
        return new ResponseEntity<List<PostDto>>(postDtoList, HttpStatus.OK);
    }

    //post image upload
    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadImage(@PathVariable Integer postId,@RequestParam("image")MultipartFile image) throws IOException {

        //getting the save post to the db
        PostDto postDto = this.postService.getPostById(postId);

        //getting the file name to the request and call the file service
        String fileName = this.fileService.uploadImage(path, image);

        //set the image path
        postDto.setPostImageName(fileName);

        //updated post save in db
        PostDto updatedPostDto = this.postService.updatePost(postDto, postId);

        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
    }

    //serve image
    //method to serve the file
    @GetMapping(value = "/posts/images/{imageName}", produces=MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
