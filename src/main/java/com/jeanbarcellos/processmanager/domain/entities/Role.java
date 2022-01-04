package com.jeanbarcellos.processmanager.domain.entities;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

import com.jeanbarcellos.processmanager.domain.enums.PermissionType;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

@Entity
@Getter
@Table(name = "role", uniqueConstraints = { @UniqueConstraint(name = "role_name_uk", columnNames = { "name" }) })
public class Role implements GrantedAuthority, RoleHierarchy {

    public static String NAME_PREFIX = "ROLE_";

    // #region Properties

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    @SequenceGenerator(name = "role_id_seq", sequenceName = "role_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER) // fix: EAGER por causa do Hibernate
    @JoinTable(name = "role_hierarchy", joinColumns = @JoinColumn(name = "parent_role_id", columnDefinition = "integer"), foreignKey = @ForeignKey(name = "role_hierarchy_parent_role_id_fk"), inverseJoinColumns = @JoinColumn(name = "child_role_id", columnDefinition = "integer"), inverseForeignKey = @ForeignKey(name = "role_hierarchy_child_role_id_fk"))
    private Set<Role> childRoles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_hierarchy", joinColumns = @JoinColumn(name = "child_role_id"), inverseJoinColumns = @JoinColumn(name = "parent_role_id"))
    private Set<Role> parentRoles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER) // fix: EAGER por causa do Hibernate
    @JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id", columnDefinition = "integer"), foreignKey = @ForeignKey(name = "role_permission_role_id_fk"), inverseJoinColumns = @JoinColumn(name = "permission_id", columnDefinition = "integer"), inverseForeignKey = @ForeignKey(name = "role_permission_permission_id_fk"))
    private Set<Permission> permissions = new HashSet<>();

    // #endregion

    // #region Contructors

    protected Role() {
    }

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Role(Integer id, String name, String description) {
        this(name, description);
        this.id = id;
    }

    // #endregion

    // #region Equals and ToString

    @Override
    public String toString() {
        return "Role [id=" + id + ", name=" + name + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Role other = (Role) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    // #endregion

    // #region Implements GrantedAuthority

    @Override
    public String getAuthority() {
        return this.name;
    }

    // #endregion

    // #region Implements RoleHierarchy

    @Override
    public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(
            Collection<? extends GrantedAuthority> authorities) {

        var authoritiesLocal = new HashSet<GrantedAuthority>(authorities);

        for (Role childRole : this.childRoles) {
            authoritiesLocal.add(childRole);

            if (!childRole.getChildRoles().isEmpty()) {
                authoritiesLocal.addAll(
                        childRole.getReachableGrantedAuthorities(authoritiesLocal));
            }
        }

        return authoritiesLocal;
    }

    // #endregion

    // #region Handler Child and Parent roles

    public Set<Role> getChildRoles() {
        return Collections.unmodifiableSet(this.childRoles);
    }

    public Role addChild(Role role) {
        if (!this.getName().equals(role.getName())) {
            this.childRoles.add(role);
            // role.parentRoles.add(this); // Erro na persistência
        }

        return this;
    }

    public boolean hasChild(Role role) {
        return this.childRoles.contains(role);
    }

    public boolean hasChild(String roleName) {
        return this.childRoles.contains(new Role(roleName, ""));
    }

    public Role clearChildRoles() {
        this.childRoles.clear();
        return this;
    }

    public Set<Role> getParentRoles() {
        return Collections.unmodifiableSet(this.parentRoles);
    }

    public Role addParent(Role role) {
        if (!this.getName().equals(role.getName())) {
            this.parentRoles.add(role);
            // role.childRoles.add(this); // Erro na persistência
        }

        return this;
    }

    public boolean hasParent(Role role) {
        return this.parentRoles.contains(role);
    }

    public boolean hasParent(String roleName) {
        return this.parentRoles.contains(new Role(roleName, ""));
    }

    public Role clearParentRoles() {
        this.parentRoles.clear();
        return this;
    }

    public Set<Role> getReachableRoles() {
        var reachableRoles = new HashSet<Role>();

        for (Role childRole : this.childRoles) {
            reachableRoles.add(childRole);

            if (!childRole.getChildRoles().isEmpty()) {
                reachableRoles.addAll(childRole.getReachableRoles());
            }
        }

        return reachableRoles;
    }

    // #endregion

    // #region Handler permissions

    public Set<Permission> getPermissions() {
        return Collections.unmodifiableSet(this.permissions);
    }

    public List<String> getPermissionNames() {
        return this.permissions.stream().map(x -> x.getName()).collect(Collectors.toList());
    }

    public Set<Permission> getReachablePermissions() {
        var reachablePermissions = new HashSet<Permission>();
        reachablePermissions.addAll(this.getPermissions());

        for (Role role : this.getReachableRoles()) {
            reachablePermissions.addAll(role.getPermissions());
        }

        return reachablePermissions;
    }

    public List<String> getReachablePermissionNames() {
        return this.getReachablePermissions().stream().map(x -> x.getName()).collect(Collectors.toList());
    }

    /**
     * Retorna um dicionário/mapa contendo a informação se a permissão é do próprio
     * perfil ou se foi herdado
     */
    public Map<Permission, PermissionType> getPermissionsWithTypes() {
        var reachablePermissions = new HashMap<Permission, PermissionType>();

        for (Role role : this.getReachableRoles()) {
            for (Permission permission : role.getPermissions()) {
                reachablePermissions.put(permission, PermissionType.INHERITED);
            }
        }

        for (Permission permission : this.permissions) {
            reachablePermissions.put(permission, PermissionType.OWN);
        }

        return reachablePermissions;
    }

    public Role addPermission(Permission permission) {
        this.permissions.add(permission);
        return this;
    }

    public boolean hasPermission(Permission permission) {
        return this.permissions.contains(permission);
    }

    public boolean hasPermission(String permissionName) {
        return this.permissions.contains(new Permission(permissionName, ""));
    }

    public Role removePermission(Permission permission) {
        this.permissions.remove(permission);
        return this;
    }

    public Role clearPermissions() {
        this.permissions.clear();
        return this;
    }

    // #endregion

}
