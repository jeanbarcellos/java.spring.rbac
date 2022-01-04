package com.jeanbarcellos.processmanager.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jeanbarcellos.processmanager.domain.entities.Role;

public class RoleHierarchyUtils {

    public static List<String> getRoleHierarchy(Role role) {
        var list = new ArrayList<String>();

        for (Role childRole : role.getChildRoles()) {
            list.add(role.getName() + " > " + childRole.getName());
        }

        return list;
    }

    public static List<String> createListRoleHierarchies(List<Role> roles) {
        List<String> roleHierarchies = new ArrayList<String>();

        // Act
        for (Role role : roles) {
            for (String roleHierarchy : getRoleHierarchy(role)) {
                roleHierarchies.add(roleHierarchy);
            }

        }

        return roleHierarchies;
    }

    public static String roleHierarchyFromMap(List<Role> roles) {
        Set<String> roleMapping = new HashSet<String>();

        // Act
        for (Role role : roles) {
            for (String roleHierarchy : getRoleHierarchy(role)) {
                roleMapping.add(roleHierarchy);
            }
        }

        StringWriter roleHierachyDescriptionBuffer = new StringWriter();
        PrintWriter roleHierarchyDescriptionWriter = new PrintWriter(roleHierachyDescriptionBuffer);

        for (String roleMap : roleMapping) {
            roleHierarchyDescriptionWriter.println(roleMap);
        }

        return roleHierachyDescriptionBuffer.toString();
    }

}
