-- Insert roles
INSERT INTO roles (name)
VALUES
    ('ADMIN'),
    ('EMPLOYEE');

-- Insert capabilities
INSERT INTO capabilities (name, description)
VALUES
    ('INSERT_ANALYST', 'Create a new analyst'),
    ('VIEW_ANALYSTS', 'View analyst list and details'),
    ('EDIT_ANALYST', 'Modify existing analyst'),
    ('DELETE_ANALYST', 'Remove an analyst');

-- Assign capabilities to ADMIN (all capabilities)
INSERT INTO roles_capabilities (role_id, capability_id)
SELECT r.id, c.id
FROM roles r
JOIN capabilities c
WHERE r.name = 'ADMIN';

-- Assign limited capabilities to EMPLOYEE
INSERT INTO roles_capabilities (role_id, capability_id)
SELECT r.id, c.id
FROM roles r
JOIN capabilities c
WHERE r.name = 'EMPLOYEE'
  AND c.name IN ('VIEW_ANALYSTS');
