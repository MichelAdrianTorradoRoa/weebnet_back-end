package com.c3.weebnet_backend.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for user login.
 * This class represents the data required for a user to log in, including their username/email and password.
 * It also includes validation constraints to ensure the data is not blank.
 */
public class UserLoginDTO {

    @NotBlank(message = "Username or Email is required") // Ensures the field is not blank and provides a custom error message.
    private String usernameOrEmail; // The username or email of the user.

    @NotBlank(message = "Password is required") // Ensures the field is not blank and provides a custom error message.
    private String password; // The password of the user.

    /**
     * Default constructor.
     * Required for deserialization (e.g., when receiving JSON data in a request).
     */
    public UserLoginDTO() {
    }

    /**
     * Parameterized constructor.
     * @param usernameOrEmail The username or email of the user.
     * @param password The password of the user.
     */
    public UserLoginDTO(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

    /**
     * Gets the username or email.
     * @return The username or email.
     */
    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    /**
     * Sets the username or email.
     * @param usernameOrEmail The username or email to set.
     */
    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    /**
     * Gets the password.
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}