package com.example.rollingFoods.rollingFoodsApp.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.*;


@Entity
@Table(name = "user_credentials")
public class UserCredential implements UserDetails {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(name = "url_profile_picture")
    private String urlProfilePicture;
    @Column(name = "auth_provider")
    private String authProvider;
    private Boolean enabled = false;


    //Relation OneToMany avec LocationOwner car un user peut avoir plusieurs locationOwner
    @OneToMany(mappedBy = "userCredential", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LocationOwner> locationOwners = new HashSet<>();


    //Relation OneToMany avec FoodTruckOwner car un user peut avoir plusieurs foodTruckOwner
    @OneToMany(mappedBy = "userCredential", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FoodTruckOwner> foodTruckOwners = new HashSet<>();

    //Relation OneToMany avec Customer car un user peut avoir plusieurs customer
    @OneToMany(mappedBy = "userCredential", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Customer> customers = new HashSet<>();

    //Relation OneToMany avec Favorite car un user peut avoir plusieurs favorite
    @OneToMany(mappedBy = "userCredential", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Favorite> favorites = new HashSet<>();




    //Relation ManyToMany avec Role car un user peut avoir plusieurs roles
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public UserCredential() {
    }

    public UserCredential(Long id, String username, String email, String password, Boolean enabled, Set<Role> roles, String urlProfilePicture, String authProvider) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
        this.urlProfilePicture = urlProfilePicture;
        this.authProvider = authProvider;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<LocationOwner> getLocationOwners() {
        return locationOwners;
    }

    public void setLocationOwners(Set<LocationOwner> locationOwners) {
        this.locationOwners = locationOwners;
    }

    public Set<FoodTruckOwner> getFoodTruckOwners() {
        return foodTruckOwners;
    }

    public void setFoodTruckOwners(Set<FoodTruckOwner> foodTruckOwners) {
        this.foodTruckOwners = foodTruckOwners;
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
