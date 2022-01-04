package com.jeanbarcellos.processmanager.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;

import com.jeanbarcellos.processmanager.domain.enums.PermissionType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RoleTests {

    Role roleAdmin;
    Role roleEditor;
    Role roleAuthor;
    Role roleViewer;
    Permission permissionView;
    Permission permissionEdit;
    Permission permissionOwnEdit;
    Permission permissionPublish;
    Permission permissionOwnPublish;
    Permission permissionDelete;

    @BeforeAll
    public void setUp() {
        permissionView = new Permission("post.view", "Ver qualquer postagem");
        permissionEdit = new Permission("post.edit", "Editar qualquer postagem");
        permissionOwnEdit = new Permission("post.own.edit", "Editar apenas as postagens próprias");
        permissionPublish = new Permission("post.publish", "Publicar qualquer postagem");
        permissionOwnPublish = new Permission("post.own.publish", "Publicar apenas postagens próprias");
        permissionDelete = new Permission("post.delete", "Excluir qualquer postagem");

        roleViewer = new Role("viewer",
                "Pode ler qualquer postagem e não pode fazer mais nada.");
        roleAuthor = new Role("author",
                "Pode ver as postagens, além de criar uma postagem, editá-la e finalmente publicá-la.");
        roleEditor = new Role("editor",
                "Pode ver as postagens, além de editar e publicar qualquer postagem.");
        roleAdmin = new Role("administrator",
                "Pode fazer qualquer coisa que um Visualizador e Editor podem fazer, além de excluir postagens.");

        // Criando a hierarquia de roles
        roleAuthor.addChild(roleViewer);
        roleEditor.addChild(roleViewer);
        roleAdmin.addChild(roleEditor);

        // Atribuindo permissões as funcções.

        // Viewer
        roleViewer.addPermission(permissionView);
        // Author.
        roleAuthor.addPermission(permissionOwnEdit);
        roleAuthor.addPermission(permissionOwnPublish);
        // Editor.
        roleEditor.addPermission(permissionEdit);
        roleEditor.addPermission(permissionPublish);
        // Administrator.
        roleAdmin.addPermission(permissionDelete);
    }

    // #region Constructores

    @Test
    void construct_entryWithNameAndDescription_shouldReturnNewRole() {

        // Arrange
        String name = "admin";
        String description = "Descrição do perfil";

        // Act
        Role role = new Role(name, description);

        // Assert
        assertEquals(name, role.getName());
        assertEquals(description, role.getDescription());
    }

    @Test
    void construct_entryWithIdAndNameAndDescription_shouldReturnNewRole() {

        // Arrange
        Integer id = 1;
        String name = "admin";
        String description = "Descrição do perfil";

        // Act
        Role role = new Role(id, name, description);

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
        Role role1 = new Role("admin", "Description");
        Role role2 = new Role("admin", "Description");

        // Act
        boolean actual = role1.equals(role2);

        // Assert
        assertTrue(actual);
    }

    @Test
    public void equals_differentNames_shouldReturnFalse() {

        // Arrange
        Role role1 = new Role("admin", "Description");
        Role role2 = new Role("triador", "Description");

        // Act
        boolean actual = role1.equals(role2);

        // Assert
        assertFalse(actual);
    }

    // #endregion

    // #region Implments GrantedAuthority, RoleHierarchy

    @Test
    public void getAuthority_roleWithName_shouldReturnValueEqualsName() {

        // Arrange
        String name = "admin";
        String description = "Descrição do perfil";

        // Act
        Role role = new Role(name, description);

        // Assert
        assertEquals(name, role.getAuthority());
    }

    @Test
    public void getReachableGrantedAuthorities_addFourRolesInHierarchy_shouldReturnListWithThreeRoles() {
        // Arrange
        Role role1 = new Role("nivel.01", "Nível 1");
        Role role2 = new Role("nivel.02", "Nível 2");
        Role role3 = new Role("nivel.03", "Nível 3");
        Role role4 = new Role("movel.04", "Nível 4");

        role1.addChild(role2);
        role2.addChild(role3);
        role3.addChild(role4);

        // Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        var result = role1.getReachableGrantedAuthorities(new HashSet<GrantedAuthority>());

        assertEquals(3, result.size());
    }

    // #endregion

    // #region Mehtods handler Child and Parent roles

    @Test
    public void addChildRole_additionOfTwoDifferentChildRoles_shouldContainTwoChildren() {
        // Arrange
        Role role1 = new Role("pai", "Pai");
        Role role2 = new Role("filho01", "Filho 01");
        Role role3 = new Role("filho02", "Filho 02");

        // Act
        role1.addChild(role2);
        role1.addChild(role3);

        // Assert
        assertEquals(2, role1.getChildRoles().size());
        assertTrue(role1.hasChild(role2));
        assertTrue(role1.hasChild(role3));
    }

    @Test
    public void addChildRole_additionOfTwoEqualChildRoles_shouldContainOneChildren() {
        // Arrange
        Role role1 = new Role("pai", "Pai");
        Role role2 = new Role("filho01", "Filho 01");
        Role role3 = new Role("filho01", "Filho 01");

        // Act
        role1.addChild(role2);
        role1.addChild(role3);

        // Assert
        assertEquals(1, role1.getChildRoles().size());
    }

    @Test
    public void addChildRole_addThreeRolesInHierarchy_shouldReturnRolesInHierarchy() {
        // Arrange
        Role role1 = new Role("pai", "Pai");
        Role role2 = new Role("filho", "Filho");
        Role role3 = new Role("neto", "Neto");

        // Act
        role2.addChild(role3);
        role1.addChild(role2);

        // Assert
        assertEquals(1, role1.getChildRoles().size());
        assertEquals(1, role2.getChildRoles().size());

        assertTrue(role1.hasChild(role2));
        assertTrue(role2.hasChild(role3));
    }

    @Test
    public void addChildRole_addChildDirectly_showuldThrowException() {
        // Arrange
        Role role1 = new Role("pai", "Pai");
        Role role2 = new Role("filho", "Filho");

        // Act && Assert
        assertThrows(UnsupportedOperationException.class, () -> {
            role1.getChildRoles().add(role2);
        });
    }

    @Test
    public void addParentRole_additionOfTwoDifferentParentRoles_shouldContainTwoParents() {
        // Arrange
        Role role1 = new Role("filho01", "Filho 01");
        Role role2 = new Role("pai01", "Pai 01");
        Role role3 = new Role("pai02", "Pai 02");

        // Act
        role1.addParent(role2);
        role1.addParent(role3);

        // Assert
        assertEquals(2, role1.getParentRoles().size());
        assertTrue(role1.hasParent(role2));
        assertTrue(role1.hasParent(role3));
    }

    @Test
    public void addParentRole_additionOfTwoEqualParentRoles_shouldContainOneParents() {
        // Arrange
        Role role1 = new Role("filho", "Filho");
        Role role2 = new Role("pai01", "Pai 01");
        Role role3 = new Role("pai01", "Pai 01");

        // Act
        role1.addParent(role2);
        role1.addParent(role3);

        // Assert
        assertEquals(1, role1.getParentRoles().size());
    }

    @Test
    public void addParentRole_addThreeRolesInHierarchy_shouldReturnRolesInHierarchy() {
        // Arrange
        Role role1 = new Role("neto", "Neto");
        Role role2 = new Role("filho", "Filho");
        Role role3 = new Role("pai", "Pai");

        // Act
        role1.addParent(role2);
        role2.addParent(role3);

        // Assert
        assertEquals(1, role1.getParentRoles().size());
        assertEquals(1, role2.getParentRoles().size());

        assertTrue(role1.hasParent(role2));
        assertTrue(role2.hasParent(role3));
    }

    @Test
    public void addParentRole_addParentDirectly_shouldThrowException() {
        // Arrange
        Role role1 = new Role("filho", "Filho");
        Role role2 = new Role("pai", "Pai");

        // Act && Assert
        assertThrows(UnsupportedOperationException.class, () -> {
            role1.getParentRoles().add(role2);
        });
    }

    @Test
    public void getReachableRoles_addFourRolesInHierarchy_shouldReturnListWithThreeRoles() {
        // Arrange
        Role role1 = new Role("nivel.01", "Nível 1");
        Role role2 = new Role("nivel.02", "Nível 2");
        Role role3 = new Role("nivel.03", "Nível 3");
        Role role4 = new Role("movel.04", "Nível 4");

        role1.addChild(role2);
        role2.addChild(role3);
        role3.addChild(role4);

        // Act
        var result = role1.getReachableRoles();

        // Assert
        assertEquals(3, result.size());
    }

    // #endregion

    // #region Mehods handler Permission

    @Test
    public void addPermission_additionOfThreeDifferentPermissions_shouldContainThreePermissions() {
        // Arrange
        Role role = new Role("admin", "Admin");

        Permission permission1 = new Permission("post.namage", "Gerenciar posts");
        Permission permission2 = new Permission("post.view", "Ver qualquer post");
        Permission permission3 = new Permission("post.on.view", "Ver somente os prórpios posts");

        // Act
        role.addPermission(permission1);
        role.addPermission(permission2);
        role.addPermission(permission3);

        // Assert
        assertEquals(3, role.getPermissions().size());
        assertTrue(role.hasPermission(permission1));
        assertTrue(role.hasPermission(permission2));
        assertTrue(role.hasPermission(permission3));
    }

    @Test
    public void addPermission_additionOfTwoEqualsPermissions_shouldContainTwoPermissions() {
        // Arrange
        Role role = new Role("admin", "Admin");

        Permission permission1 = new Permission("post.namage", "Gerenciar posts");
        Permission permission2 = new Permission("post.view", "Ver qualquer post");
        Permission permission3 = new Permission("post.view", "Ver qualquer post");

        // Act
        role.addPermission(permission1);
        role.addPermission(permission2);
        role.addPermission(permission3);

        // Assert
        assertEquals(2, role.getPermissions().size());
        assertTrue(role.hasPermission(permission1));
        assertTrue(role.hasPermission(permission2));
    }

    @Test
    public void getPermissionNames_entryThreePermissions_shouldReturnListWithThreeNames() {
        // Arrange
        Role role = new Role("admin", "Admin");

        Permission permission1 = new Permission("post.namage", "Gerenciar posts");
        Permission permission2 = new Permission("post.view", "Ver qualquer post");
        Permission permission3 = new Permission("post.on.view", "Ver somente os prórpios posts");

        role.addPermission(permission1);
        role.addPermission(permission2);
        role.addPermission(permission3);

        // Act
        List<String> result = role.getPermissionNames();

        // Assert
        assertEquals(3, result.size());
        assertTrue(result.contains(permission1.getName()));
        assertTrue(result.contains(permission2.getName()));
        assertTrue(result.contains(permission3.getName()));
    }

    @Test
    public void hasPermission_entryString_shouldReturnTrue() {
        var result = this.roleAdmin.hasPermission(permissionDelete.getName());

        assertTrue(result);
    }

    @Test
    public void hasPermission_entryString_shouldReturnFalse() {
        var result = this.roleAdmin.hasPermission("asdad");

        assertFalse(result);
    }

    @Test
    @DisplayName("Obter permissões de um perfil")
    public void getPermission_shouldReturnSetContainsOnePermissions() {
        // Arrange && Act
        var result = roleAdmin.getPermissions();

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.contains(permissionDelete));
    }

    @Test
    @DisplayName("Obter permissões alcencáveis de um perfil")
    public void getReachablePermissions_shouldReturnSetContainsFourPermissions() {
        // Arrange && Act
        var result = this.roleAdmin.getReachablePermissions();

        // Assert
        assertEquals(4, result.size());
        assertTrue(result.contains(permissionDelete));
        assertTrue(result.contains(permissionPublish));
        assertTrue(result.contains(permissionEdit));
        assertTrue(result.contains(permissionView));
    }

    @Test
    @DisplayName("Obter permissões alcencáveis de um perfil juntamente com seu tipo")
    public void getPermissionsWithTypes_shouldReturnMapContainsFourPermissionsWithTypes() {
        // Arrange && Act
        var result = this.roleAdmin.getPermissionsWithTypes();

        // Assert
        assertEquals(4, result.size());
        assertTrue(result.containsKey(permissionDelete));
        assertTrue(result.containsKey(permissionPublish));
        assertTrue(result.containsKey(permissionEdit));
        assertTrue(result.containsKey(permissionView));
        assertEquals(PermissionType.OWN, result.get(permissionDelete));
        assertEquals(PermissionType.INHERITED, result.get(permissionPublish));
        assertEquals(PermissionType.INHERITED, result.get(permissionEdit));
        assertEquals(PermissionType.INHERITED, result.get(permissionView));
    }

    // #endregion

}
