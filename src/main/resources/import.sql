
-- ----------------------------
-- Records of permission
-- ----------------------------

INSERT INTO "public"."permission"("id", "name", "description") VALUES (1, 'permission.manage', 'Manipular permissões');
INSERT INTO "public"."permission"("id", "name", "description") VALUES (2, 'role.manage', 'Manipular perfils');
INSERT INTO "public"."permission"("id", "name", "description") VALUES (3, 'user.manage', 'Manipular usuários');
--
INSERT INTO "public"."permission"("id", "name", "description") VALUES (4, 'category.manage', 'Manipular categorias de postagens');

INSERT INTO "public"."permission"("id", "name", "description") VALUES (5, 'post.view', 'Ver qualquer postagem.');
INSERT INTO "public"."permission"("id", "name", "description") VALUES (6, 'post.edit', 'Editar qualquer postagem.');
INSERT INTO "public"."permission"("id", "name", "description") VALUES (7, 'post.own.edit', 'Edite apenas as postagens próprias.');
INSERT INTO "public"."permission"("id", "name", "description") VALUES (8, 'post.publish', 'Publique qualquer postagem.');
INSERT INTO "public"."permission"("id", "name", "description") VALUES (9, 'post.own.publish', 'Publicar apenas postagens próprias.');
INSERT INTO "public"."permission"("id", "name", "description") VALUES (10, 'post.delete', 'Exclua qualquer postagem.');

SELECT setval('permission_id_seq', 10, true);


-- ----------------------------
-- Records of role
-- ----------------------------

INSERT INTO "public"."role" ("id", "name", "description") VALUES (1, 'root', 'Perfil do desenvolvedor do sistema');
INSERT INTO "public"."role" ("id", "name", "description") VALUES (2, 'administrator', 'Pode fazer qualquer coisa que um Visualizador e Editor podem fazer, além de excluir postagens.');
INSERT INTO "public"."role" ("id", "name", "description") VALUES (3, 'editor', 'Pode ver as postagens, além de editar e publicar qualquer postagem.');
INSERT INTO "public"."role" ("id", "name", "description") VALUES (4, 'author', 'Pode ver as postagens, além de criar uma postagem, editá-la e finalmente publicá-la.');
INSERT INTO "public"."role" ("id", "name", "description") VALUES (5, 'viewer', 'Pode ver as postagens, além de criar uma postagem, editá-la e finalmente publicá-la.');

SELECT setval('role_id_seq', 5, true);


-- ----------------------------
-- Records of role_hierarchy
-- ----------------------------

INSERT INTO "public"."role_hierarchy"("parent_role_id", "child_role_id") VALUES (1, 2);
INSERT INTO "public"."role_hierarchy"("parent_role_id", "child_role_id") VALUES (2, 3);
INSERT INTO "public"."role_hierarchy"("parent_role_id", "child_role_id") VALUES (3, 5);
INSERT INTO "public"."role_hierarchy"("parent_role_id", "child_role_id") VALUES (4, 5);


-- ----------------------------
-- Records of role_permission
-- ----------------------------

INSERT INTO "public"."role_permission"("role_id", "permission_id") VALUES (1, 1);
INSERT INTO "public"."role_permission"("role_id", "permission_id") VALUES (1, 2);
INSERT INTO "public"."role_permission"("role_id", "permission_id") VALUES (1, 3);

INSERT INTO "public"."role_permission"("role_id", "permission_id") VALUES (2, 4);
INSERT INTO "public"."role_permission"("role_id", "permission_id") VALUES (2, 10);
INSERT INTO "public"."role_permission"("role_id", "permission_id") VALUES (3, 6);
INSERT INTO "public"."role_permission"("role_id", "permission_id") VALUES (3, 8);
INSERT INTO "public"."role_permission"("role_id", "permission_id") VALUES (4, 7);
INSERT INTO "public"."role_permission"("role_id", "permission_id") VALUES (4, 8);
INSERT INTO "public"."role_permission"("role_id", "permission_id") VALUES (5, 5);


-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO "public"."user" ("id", "email", "name", "password", "status") VALUES (1, 'root@test.com', 'Root/Developer User', '$2a$10$rIw0H6cXzblKeHJuf9usfuNKDCIFrr2nFszv2uRI6QAhhKX6dyW5G', 1);
INSERT INTO "public"."user" ("id", "email", "name", "password", "status") VALUES (2, 'admin@test.com', 'Administrador User', '$2a$10$rIw0H6cXzblKeHJuf9usfuNKDCIFrr2nFszv2uRI6QAhhKX6dyW5G', 1);
INSERT INTO "public"."user" ("id", "email", "name", "password", "status") VALUES (3, 'editor@test.com', 'Editor User', '$2a$10$rIw0H6cXzblKeHJuf9usfuNKDCIFrr2nFszv2uRI6QAhhKX6dyW5G', 1);
INSERT INTO "public"."user" ("id", "email", "name", "password", "status") VALUES (4, 'author@test.com', 'Author User', '$2a$10$rIw0H6cXzblKeHJuf9usfuNKDCIFrr2nFszv2uRI6QAhhKX6dyW5G', 1);
INSERT INTO "public"."user" ("id", "email", "name", "password", "status") VALUES (5, 'viewer@test.com', 'Viewer User', '$2a$10$rIw0H6cXzblKeHJuf9usfuNKDCIFrr2nFszv2uRI6QAhhKX6dyW5G', 1);


SELECT setval('user_id_seq', 5, true);

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO "public"."user_role" ("user_id", "role_id") VALUES (1, 1);
INSERT INTO "public"."user_role" ("user_id", "role_id") VALUES (2, 2);
INSERT INTO "public"."user_role" ("user_id", "role_id") VALUES (3, 3);
INSERT INTO "public"."user_role" ("user_id", "role_id") VALUES (4, 4);
INSERT INTO "public"."user_role" ("user_id", "role_id") VALUES (5, 5);





-- ----------------------------
-- Records of process
-- ----------------------------

INSERT INTO "public"."process"("id", "number", "subject", "description", "created_user_id", "created_at", "updated_at", "finished_user_id", "finished_opinion", "finished_at") VALUES (1, '1', 'Descrição', 'Assunto', 1, '2021-12-23 20:06:52.043976', '2021-12-23 20:06:52.043976', NULL, NULL, NULL);
INSERT INTO "public"."process"("id", "number", "subject", "description", "created_user_id", "created_at", "updated_at", "finished_user_id", "finished_opinion", "finished_at") VALUES (2, '2', 'Descrição', 'Assunto', 2, '2021-12-23 20:06:52.043976', '2021-12-23 20:06:52.043976', NULL, NULL, NULL);
INSERT INTO "public"."process"("id", "number", "subject", "description", "created_user_id", "created_at", "updated_at", "finished_user_id", "finished_opinion", "finished_at") VALUES (3, '3', 'Descrição', 'Assunto', 3, '2021-12-23 20:06:52.043976', '2021-12-23 20:06:52.043976', NULL, NULL, NULL);

SELECT setval('process_id_seq', 3, true);