INSERT INTO admin.permission(permission_id, grouped, role, description, created_date, created_by, last_modified_date,
                             last_modified_by)
values (nextval('admin.permission_id_seq'), 'Utilisateurs - Administration', 'Réinitialiser-Mot-Passe',
        'Réinitialiser le Mot de Passe', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system');


