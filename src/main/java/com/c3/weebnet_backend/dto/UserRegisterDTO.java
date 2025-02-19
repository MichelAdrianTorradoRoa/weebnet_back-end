package com.c3.weebnet_backend.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

import io.micrometer.common.lang.Nullable;

public class UserRegisterDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    private String username;

    @NotBlank(message = "Full name is required")
    @Size(min = 5, max = 100, message = "Full name must be between 5 and 100 characters")
    private String fullname;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String cellphone;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 12, message = "Password must be between 8 and 12 characters")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[%$;&.,#]).{8,12}$",
        message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character (%$;&.,#)"
    )
    private String password;

    @Nullable
    @Size(max = 255, message = "Bio must not exceed 255 characters")
    private String bio;
    
    
    @Nullable
    @Pattern(regexp = "^(https?|ftp)://.*$", message = "Photo must be a valid URL")
    private String photo;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(String username, String fullname, String cellphone, String email, LocalDate birthDate, String password, String bio, String photo) {
        this.username = username;
        this.fullname = fullname;
        this.cellphone = cellphone;
        this.email = email;
        this.birthDate = birthDate;
        this.password = password;
        this.bio = bio;
        this.photo = photo;
    }
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    
}
