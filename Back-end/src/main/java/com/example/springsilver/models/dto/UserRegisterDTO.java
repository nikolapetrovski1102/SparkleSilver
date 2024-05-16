package com.example.springsilver.models.dto;

import com.example.springsilver.models.enumeration.UserRoles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {

    @NotBlank
    @Size(min = 4, max = 50)
    private String username;

    @NotBlank
    private String firstName;

    private String lastName;

    @NotBlank
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String email;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;

    @NotBlank
    @Size(min = 8, max = 100)
    private String repeatPassword;

    @NotNull
    private Integer userRoles;

    private String imagePathUrl;

    private boolean isActive;

}
