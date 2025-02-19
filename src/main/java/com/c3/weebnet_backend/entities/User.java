package com.c3.weebnet_backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Full name is required")
    @Size(min = 5, max = 100, message = "Full name must be between 5 and 100 characters")
    private String fullname;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String cellphone;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull(message = "Birth date is required")
    @Column(name = "birth_day")
    private LocalDate birthDate;

    @NotBlank(message = "Password is required")
    private String password;

    @Size(max = 255, message = "Bio must not exceed 255 characters")
    private String bio;

    @Column(nullable = true)
    @Pattern(regexp = "^(https?|ftp)://.*$", message = "Photo must be a valid URL")
    private String photo;

    public User() {
    }

    // Constructor sin roles
    public User(String username, String fullname, String cellphone, String email, LocalDate birthDate, String password) {
        this.username = username;
        this.fullname = fullname;
        this.cellphone = cellphone;
        this.email = email;
        this.birthDate = birthDate;
        this.password = password;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username='" + username + '\'' + ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' + ", birthDate=" + birthDate + ", bio='" + bio + '\'' + 
                ", photo='" + photo + '\'' + '}';
    }
}
