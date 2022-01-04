package com.jeanbarcellos.processmanager.domain.entities;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.jeanbarcellos.processmanager.domain.enums.UserStatus;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

@Entity
@Getter
@Table(name = "\"user\"", uniqueConstraints = { @UniqueConstraint(name = "user_email_uk", columnNames = { "email" }), })
public class User implements UserDetails {

    // #region Properties

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", updatable = false)
    private Integer id;

    private String name;

    @Column(name = "email")
    private String email;

    private String password;

    @Column(name = "status")
    private UserStatus status = UserStatus.INACTIVE;

    @ManyToMany(fetch = FetchType.EAGER) // fix: EAGER por causa do Hibernate
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", columnDefinition = "integer"), foreignKey = @ForeignKey(name = "user_role_user_id_fk"), inverseJoinColumns = @JoinColumn(name = "role_id", columnDefinition = "integer"), inverseForeignKey = @ForeignKey(name = "user_role_role_id_fk"))
    private Set<Role> roles = new HashSet<>();

    // #endregion

    // #region Contructors

    protected User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = UserStatus.INACTIVE;
    }

    public User(String name, String email, String password, UserStatus status) {
        this(name, email, password);
        this.status = status;
    }

    public User(Integer id, String name, String email, String password, UserStatus status) {
        this(name, email, password, status);
        this.id = id;
    }

    // #endregion

    // #region Setters

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    // #endregion

    // #region Handler Roles

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(this.roles);
    }

    public Set<Role> getReachableRoles() {
        var reachableRoles = new HashSet<Role>();

        for (Role role : this.roles) {
            reachableRoles.add(role);
            reachableRoles.addAll(role.getReachableRoles());
        }

        return reachableRoles;
    }

    public List<String> getRoleNames() {
        return this.roles.stream().map(x -> x.getName()).collect(Collectors.toList());
    }

    public List<String> getReachableRoleNames() {
        return this.getReachableRoles().stream().map(x -> x.getName()).collect(Collectors.toList());
    }

    public User addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public boolean hasRole(Role role) {
        return this.roles.contains(role);
    }

    public User removeRole(Role role) {
        this.roles.remove(role);
        return this;
    }

    public User clearRoles() {
        this.roles.clear();
        return this;
    }

    // #endregion

    // #region Handler Permissions

    public Set<Permission> getPermissions() {
        var permissions = new HashSet<Permission>();

        for (Role role : this.getReachableRoles()) {
            permissions.addAll(role.getPermissions());
        }

        return permissions;
    }

    public List<String> getPermissionNames() {
        return this.getPermissions().stream().map(x -> x.getName()).collect(Collectors.toList());
    }

    // #endregion

    // #region Handler Status

    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

    public User activate() {
        this.status = UserStatus.ACTIVE;
        return this;
    }

    public User inactivate() {
        this.status = UserStatus.INACTIVE;
        return this;
    }

    // #endregion

    // #region implements UserDetails

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        var roles = this.getReachableRoleNames().stream()
                .map(name -> new SimpleGrantedAuthority(Role.NAME_PREFIX + name))
                .collect(Collectors.toSet());

        var permissions = this.getPermissionNames().stream()
                .map(name -> new SimpleGrantedAuthority(name))
                .collect(Collectors.toSet());

        var all = new HashSet<SimpleGrantedAuthority>();
        all.addAll(roles);
        all.addAll(permissions);

        return all;
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
        return this.isActive();
    }

    // #endregion

    // #region Equals and ToString

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", name=" + name + "]";
    }

    // #endregion
}
