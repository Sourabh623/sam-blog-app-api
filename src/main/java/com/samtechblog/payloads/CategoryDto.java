package com.samtechblog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private int category_id;

    @NotEmpty(message = "Category name not be empty.")
    @Size(min = 2, message = "Category name must be min 2 character.")
    private String category_name;

    @NotEmpty(message = "Category description not be empty.")
    @Size(min = 10, message = "Category description must be min 10 character.")
    private String category_description;

    private Date category_created_at;
    private Date category_updated_at;
}
