package com.jeanbarcellos.processmanager.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;

@Entity
@Getter
// @Setter
@Table(name = "permission", uniqueConstraints = {
        @UniqueConstraint(name = "permission_name_uk", columnNames = { "name" }) })
public class Permission {

    // #region Properties

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_id_seq")
    @SequenceGenerator(name = "permission_id_seq", sequenceName = "permission_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    // #endregion

    // #region Construtors

    protected Permission() {
    }

    public Permission(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Permission(Integer id, String name, String description) {
        this(name, description);
        this.id = id;
    }

    // #endregion

    // #region Equals and ToString

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
        Permission other = (Permission) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Permission [id=" + id + ", name=" + name + "]";
    }

    // #endregion

}
