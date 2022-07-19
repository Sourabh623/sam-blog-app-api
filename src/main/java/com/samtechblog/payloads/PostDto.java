package com.samtechblog.payloads;
import com.samtechblog.models.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


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
    private Set<Comment> comments = new HashSet<>();
}
