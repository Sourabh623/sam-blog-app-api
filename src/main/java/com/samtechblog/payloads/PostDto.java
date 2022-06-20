package com.samtechblog.payloads;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Integer postId;
    private String postTitle;
    private String postContent;
    private String postImageName;
    private Date postCreatedAt;
    private Date postUpdatedAt;
    private CategoryDto category;
    private UserDto user;
}
