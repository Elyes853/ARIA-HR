INSERT INTO admin.profile_permission(profile_id, permission_id)
SELECT 1, permission_id
from admin.permission
where role = 'Réinitialiser-Mot-Passe';
