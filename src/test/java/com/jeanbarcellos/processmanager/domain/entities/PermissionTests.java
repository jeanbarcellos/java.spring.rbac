package com.jeanbarcellos.processmanager.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PermissionTests {

    // #region Constructores

    @Test
    void construct_entryWithNameAndDescription_shouldReturnNewPermission() {

        // Arrange
        String name = "user.manage";
        String description = "Pode manipular um usuário";

        // Act
        Permission role = new Permission(name, description);

        // Assert
        assertEquals(name, role.getName());
        assertEquals(description, role.getDescription());
    }

    @Test
    void construct_entryWithIdAndNameAndDescription_shouldReturnNewPermission() {

        // Arrange
        Integer id = 1;
        String name = "user.manage";
        String description = "Pode manipular um usuário";

        // Act
        Permission role = new Permission(id, name, description);

        // Assert
        assertEquals(id, role.getId());
        assertEquals(name, role.getName());
        assertEquals(description, role.getDescription());
    }

    // #endregion

    // #region Equals

    @Test
    public void equals_equalsNames_shouldReturnTrue() {

        // Arrange
        Permission role1 = new Permission("process.manage", "Manipular o processo");
        Permission role2 = new Permission("process.manage", "Visualizar processo");

        // Act
        boolean actual = role1.equals(role2);

        // Assert
        assertTrue(actual);
    }

    @Test
    public void equals_differentNames_shouldReturnFalse() {

        // Arrange
        Permission role1 = new Permission("process.manage", "Manipular o processo");
        Permission role2 = new Permission("process.viewer", "Visualizar processo");

        // Act
        boolean actual = role1.equals(role2);

        // Assert
        assertFalse(actual);
    }

    // #endregion
}
