package pl.sdacademy.projecteventsbackend.user;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Table(name = "users")
@Entity
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "This field must not be empty")
    @Column(unique = true)
    private String username;
    @NotBlank(message = "This field must not be empty")
    private String password;
    @Email
    @Column(unique = true)
    private String mail;
    @Column
    private Boolean isAccountNonExpired;
    @Column
    private Boolean isAccountNonLocked;
    @Column
    private Boolean isCredentialsNonExpired;
    @Column
    private Boolean isEnabled;
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "roles")
    @Column
    private Set<UserRole> roles= new HashSet<>();
    private LocalDateTime createdUserDate;
    private LocalDateTime updatedOn;


    public UserEntity() {
    }

    public UserEntity(String username, String password, String mail) {
        this.username = username;
        this.password = password;
        this.mail = mail;
    }

    public UserEntity(Long id, @NotBlank(message = "This field must not be empty") String username, @NotBlank(message = "This field must not be empty") String password, @Email String mail, Boolean isAccountNonExpired, Boolean isAccountNonLocked, Boolean isCredentialsNonExpired, Boolean isEnabled, Set<UserRole> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
        this.roles = roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public void setCreatedUserDate(LocalDateTime createdUserDate) {
        this.createdUserDate = createdUserDate;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public Boolean getAccountNonExpired() {
        return isAccountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return isAccountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public LocalDateTime getCreatedUserDate() {
        return createdUserDate;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }
}
