package com.samtechblog.models;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer postId;
    @Column(length = 255)

    private String postTitle;
    @Column(length = 10000)
    private String postContent;
    private String postImageName;
    private Date postCreatedAt;
    private Date postUpdatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
