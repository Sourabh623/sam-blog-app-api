package com.samtechblog.payloads;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@NoArgsConstructor
@Data
public class UserDto{

    private int userId;

    @NotEmpty(message = "Name not be empty.")
    @Size(min = 4, message = "Name must be min 4 character.")
    private String userName;

    @Email(message = "Invalid Email")
    @NotEmpty(message = "Email not be empty.")
    private String userEmail;

    @NotEmpty(message = "Password not be empty.")
    @Size(min = 3, max = 15, message = "password must be min 3 and max 15 character")
    private String userPassword;

    @NotEmpty(message = "About not be empty.")
    @Size(min = 10, message = "About must be min 10 character")
    private String userAbout;

    private Date userCreatedAt;
    private Date userUpdatedAt;
}
