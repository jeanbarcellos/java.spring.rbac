package com.jeanbarcellos.processmanager.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jeanbarcellos.processmanager.domain.enums.UserStatus;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTests {

    String userName = "Jean Barcellos";
    String userEmail = "jeanbarcellos@hotmail.com";
    String userPassword = "teste@123";

    User userActive;

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
    User userAdmin;
    User userEditor;
    User userAuthor;
    User userViewer;
    User userEditorAndAuthor;

    @BeforeAll
    public void setUp() {

        userActive = new User(userName, userEmail, userPassword, UserStatus.ACTIVE);

        // -----------------------

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

        userAdmin = new User("Admin", "admin@test.com", "test@123", UserStatus.ACTIVE);
        userEditor = new User("Editor", "editor@test.com", "test@123", UserStatus.ACTIVE);
        userAuthor = new User("Author", "author@test.com", "test@123", UserStatus.ACTIVE);
        userViewer = new User("Viewer", "viewer@test.com", "test@123", UserStatus.ACTIVE);
        userEditorAndAuthor = new User("Viewer and Author", "viewer.author@test.com", "test@123", UserStatus.ACTIVE);

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

        // Definindo as roles dos uusários
        userAdmin.addRole(roleAdmin);
        userAuthor.addRole(roleAuthor);
        userEditor.addRole(roleEditor);
        userViewer.addRole(roleViewer);
        userEditorAndAuthor.addRole(roleEditor);
        userEditorAndAuthor.addRole(roleAuthor);
    }

    // #region Contructors

    @Test
    public void construct_entryWithNameAndEmailAndPassword_shouldReturnCreatedUser() {
        // Arrange && Act
        User user = new User(userName, userEmail, userPassword);

        // Assert
        assertEquals(userName, user.getName());
        assertEquals(userEmail, user.getEmail());
        assertEquals(userPassword, user.getPassword());
    }

    @Test
    public void inactivate_userActive_shouldReturnInactiveUser() {
        // Arrange
        User user = new User(userName, userEmail, userPassword, UserStatus.ACTIVE);

        // Act
        user.inactivate();

        // Assert
        assertFalse(user.isActive());
        assertEquals(UserStatus.INACTIVE, user.getStatus());
    }

    @Test
    public void activate_givenUserInactive_shouldReturnActiveUser() {
        // Arrange
        User user = new User(userName, userEmail, userPassword, UserStatus.INACTIVE);

        // Act
        user.activate();

        // Assert
        assertTrue(user.isActive());
        assertEquals(UserStatus.ACTIVE, user.getStatus());
    }

    // #endregion

    // #region Handler Roles

    @Test
    public void getRoles_additionOfOneRole_shouldContainOneRole() {
        // Arrange
        User user = new User(userName, userEmail, userPassword, UserStatus.ACTIVE);

        Role role1 = new Role("nivel.01", "Nível 1");
        Role role2 = new Role("nivel.02", "Nível 2");
        Role role3 = new Role("nivel.03", "Nível 3");
        Role role4 = new Role("movel.04", "Nível 4");

        role1.addChild(role2);
        role2.addChild(role3);
        role3.addChild(role4);

        user.addRole(role1);

        // Act
        var result = user.getRoles();

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    public void getRoleNames_additionOfOneRole_shouldReturnListWithOneRole() {
        // Arrange
        User user = new User(userName, userEmail, userPassword, UserStatus.ACTIVE);

        Role role1 = new Role("nivel.01", "Nível 1");
        Role role2 = new Role("nivel.02", "Nível 2");
        Role role3 = new Role("nivel.03", "Nível 3");
        Role role4 = new Role("movel.04", "Nível 4");

        role1.addChild(role2);
        role2.addChild(role3);
        role3.addChild(role4);

        user.addRole(role1);

        // Act
        var result = user.getRoleNames();

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    public void getReachableRoles_additionOfOneRole_shouldReturnSetWithFourRoles() {
        // Arrange
        User user = new User(userName, userEmail, userPassword, UserStatus.ACTIVE);

        Role role1 = new Role("nivel.01", "Nível 1");
        Role role2 = new Role("nivel.02", "Nível 2");
        Role role3 = new Role("nivel.03", "Nível 3");
        Role role4 = new Role("movel.04", "Nível 4");

        role1.addChild(role2);
        role2.addChild(role3);
        role3.addChild(role4);

        user.addRole(role1);

        // Act
        var result = user.getReachableRoles();

        // Assert
        assertEquals(4, result.size());
    }

    @Test
    public void getReachableRoleNames_additionOfOneRole_shouldReturnListWithFourRoles() {
        // Arrange
        User user = new User(userName, userEmail, userPassword, UserStatus.ACTIVE);

        Role role1 = new Role("nivel.01", "Nível 1");
        Role role2 = new Role("nivel.02", "Nível 2");
        Role role3 = new Role("nivel.03", "Nível 3");
        Role role4 = new Role("movel.04", "Nível 4");

        role1.addChild(role2);
        role2.addChild(role3);
        role3.addChild(role4);

        user.addRole(role1);

        // Act
        var result = user.getReachableRoleNames();

        // Assert
        assertEquals(4, result.size());
    }

    // #endregion

    // #region Handler Permissions

    @Test
    public void getPermissions_userWithOnlyOneRole_shouldReturnSetContainsFourPermissions() {

        // Arrange && Act
        var result = userAdmin.getPermissions();

        // Assert
        assertEquals(4, result.size());
        assertTrue(result.contains(permissionDelete));
        assertTrue(result.contains(permissionPublish));
        assertTrue(result.contains(permissionEdit));
        assertTrue(result.contains(permissionView));
    }

    @Test
    public void getPermissions_userWithTwoRole_shouldReturnSetContainsFourPermissions() {

        // Arrange && Act
        var result = userEditorAndAuthor.getPermissions();

        // Assert
        assertEquals(5, result.size());
        assertTrue(result.contains(permissionPublish));
        assertTrue(result.contains(permissionEdit));
        assertTrue(result.contains(permissionView));
        assertTrue(result.contains(permissionOwnPublish));
        assertTrue(result.contains(permissionOwnEdit));
    }

    // #endregion

    // #region implements UserDetails

    @Test
    public void getAuthorities() {
        // Arrange && Act
        var result = userAdmin.getAuthorities();

        // Assert
        assertEquals(7, result.size()); // 4 permissions ans 3 roles
    }

    // #endregion

}
