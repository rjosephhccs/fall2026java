package com.inventory.user.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {
	
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String role;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    protected User() {
    	// Required by JPA
    }
    
    public User(String username, String passwordHash, String role, String firstName, String lastName, String email, Boolean isActive) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isActive = isActive;
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
	public Integer getUserId() { return userId; }
	@Override
	public String getUsername() { return username; }
	public void setUsername(String name) { username = name; }
	// public String getPasswordHash() { return passwordHash; }
	@Override
	public String getPassword() { return passwordHash; }
	public void setPasswordHash(String hash) { passwordHash = hash; }
	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }
	public String getFirstName() { return firstName; }
	public void setFirstName(String name) { firstName = name; }
	public String getLastName() { return lastName; }
	public void setLastName(String name) { lastName = name; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public boolean isActive() { return isActive; }
	public void setActive(boolean isActive) { this.isActive = isActive; }
	public LocalDateTime getCreatedAt() { return createdAt; }
	public LocalDateTime getUpdatedAt() { return updatedAt; }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role));
	}

	@Override
	public boolean isAccountNonLocked(){return true;}

    @Override
    public boolean isAccountNonExpired(){return true;}

    @Override
    public boolean isCredentialsNonExpired(){return true;}

    @Override
    public boolean isEnabled(){return isActive;}
}