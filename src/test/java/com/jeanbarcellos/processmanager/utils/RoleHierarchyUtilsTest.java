package com.jeanbarcellos.processmanager.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.jeanbarcellos.processmanager.domain.entities.Role;

import org.junit.jupiter.api.Test;

public class RoleHierarchyUtilsTest {

    @Test
    public void getRoleHierarchy_addFourRolesInHierarchy_shouldReturnListRoleHirearchy() {
        // Arrange
        Role role1 = new Role("nivel.01", "Nível 1");
        Role role2 = new Role("nivel.02", "Nível 2");
        Role role3 = new Role("nivel.03", "Nível 3");
        Role role4 = new Role("movel.04", "Nível 4");

        role1.addChild(role2);
        role2.addChild(role3);
        role3.addChild(role4);

        // Act
        List<String> list = RoleHierarchyUtils.getRoleHierarchy(role1);

        // // Assert
        assertEquals(1, list.size());
        assertEquals(role1.getName() + " > " + role2.getName(), list.get(0));
    }

    @Test
    public void createListRoleHierarchies_addListRolesInHierarchy_shouldReturnListRoleHirearchyWithThreeStrings() {
        // Arrange
        Role role1 = new Role("nivel.01", "Nível 1");
        Role role2 = new Role("nivel.02", "Nível 2");
        Role role3 = new Role("nivel.03", "Nível 3");
        Role role4 = new Role("movel.04", "Nível 4");

        role1.addChild(role2);
        role2.addChild(role3);
        role3.addChild(role4);

        List<Role> roles = new ArrayList<Role>();
        roles.add(role1);
        roles.add(role2);
        roles.add(role3);

        var roleHierarchies = RoleHierarchyUtils.createListRoleHierarchies(roles);

        // Assert
        assertEquals(3, roleHierarchies.size());
        assertEquals(role1.getName() + " > " + role2.getName(), roleHierarchies.get(0));
        assertEquals(role2.getName() + " > " + role3.getName(), roleHierarchies.get(1));
        assertEquals(role3.getName() + " > " + role4.getName(), roleHierarchies.get(2));
    }
}
