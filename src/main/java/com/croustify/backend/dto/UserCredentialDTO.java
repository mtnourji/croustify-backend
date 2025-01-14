package com.croustify.backend.dto;

import com.croustify.backend.models.Role;

import java.util.Set;

public class UserCredentialDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Boolean enabled;
    private String urlProfilePicture;
    private Set<Role> roles;
    private String authProvider;



    public UserCredentialDTO() {
    }

    public UserCredentialDTO(String username, String email, String password, Boolean enabled, Set<Role> roles, String urlProfilePicture, String authProvider) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
        this.urlProfilePicture = urlProfilePicture;
        this.authProvider = authProvider;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlProfilePicture() {
        return urlProfilePicture;
    }

    public void setUrlProfilePicture(String urlProfilePicture) {
        this.urlProfilePicture = urlProfilePicture;
    }

    public String getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(String authProvider) {
        this.authProvider = authProvider;
    }
}
