INSERT IGNORE INTO role (name) VALUES ('SUPER');
INSERT IGNORE INTO role (name) VALUES ('ADMIN');
INSERT IGNORE INTO role (name) VALUES ('USER');

INSERT IGNORE INTO sport_category (id, name) VALUES
 (1, 'Soccer'),
 (2, 'Basketball'),
 (3, 'Handball'),
 (4, 'Rugby'),
 (5, 'Hockey');

INSERT IGNORE INTO spectacle_category (id, name) VALUES
(1, 'Concert'),
(2, 'Theater'),
(3, 'Festival');

INSERT IGNORE INTO user
    (id, email, firstname, lastname, password)
VALUES
    (1, 'robin.foutel@gmail.com', 'Robin', 'Foutel', '$2y$10$Rd4/r3gRbLsZ8ju.Zx.Hm.ZFvaklg2ziJ6G3RMvrelGMgW1dL4AI6');

INSERT IGNORE INTO user_role
    (role_id, user_id)
VALUES (
        (SELECT id FROM role WHERE name LIKE 'SUPER'), (SELECT id FROM user WHERE email LIKE 'robin.foutel@gmail.com')
);


INSERT IGNORE INTO user
(id, email, firstname, lastname, password)
VALUES
    (2, 'test.test@test.com', 'John', 'Doe', '$2y$10$kUeYm4IN38ALaBL5vxZKPO9/2VdSmm4eT3MZySLWenuLyhLWyzS0e');

INSERT IGNORE INTO user_role
(role_id, user_id)
VALUES (
           (SELECT id FROM role WHERE name LIKE 'USER'), (SELECT id FROM user WHERE email LIKE 'test.test@test.com')
       );

INSERT IGNORE INTO address
(id, name, zipcode)
VALUES
    (1, 'Paris', '75000'),
    (2, 'Marseille', '13000'),
    (3, 'Grenoble', '38000'),
    (4, 'Bordeau', '30072'),
    (5, 'Rouen', '76000'),
    (6, 'Lyon', '69000'),
    (7, 'Caen', '14000'),
    (8, 'Carhaix', '29024');

INSERT IGNORE INTO sport
(id, name, price, start_date, end_date, description, address_id, sport_category_id, seller_id)
VALUES
    (1, 'MATCH PSG-OL', 200, '2023-10-21', '2023-10-21', 'Place VIP', 6, 1, 2),
    (2, 'MATCH FRANCE-NEW-ZEALAND', 100, '2023-11-22', '2023-11-22', 'RANG 4 Place 45', 1, 4, 2),
    (3, 'Team Esjberg VS Metz Handball', 11, '2024-04-01', '2024-04-01', 'Non placé', 6, 3, 2),
    (4, 'DRAGON DE ROUEN VS AMIENS', 25, '2023-12-08', '2023-12-08', 'Non placé', 5, 5, 2),
    (5, 'GRENOBLE VS MONTPELLIER', 25, '2023-10-25', '2023-10-25', 'Non placé', 3, 2, 2),
    (6, 'FRANCE VS ESPAGNE', 300, '2024-02-14', '2024-02-14', 'Place VIP', 1, 1, 2),
    (7, 'FRANCE VS ITALIE', 45, '2024-03-15', '2024-03-15', 'RANG 3 Place 78', 1, 3, 2);

INSERT IGNORE INTO spectacle
(id, name, price, start_date, end_date, description, address_id, spectacle_category_id, seller_id)
VALUES
    (1, 'Orelsan', 34, '2024-05-17', '2024-05-17', 'Fosse', 7, 1, 2),
    (2, 'ROMEO ET JULIETTE', 14, '2024-07-11', '2024-07-11', 'RANG 6 Place 50', 2, 2, 2),
    (3, 'JUL', 20, '2024-08-21', '2024-08-21', 'Assis', 2, 1, 2),
    (4, 'BEAUREGARD', 150, '2024-07-07', '2024-07-09', 'Pass 3 jours', 7, 3, 2),
    (5, 'NINHO', 50, '2024-09-07', '2024-09-07', 'Fosse', 6, 1, 2),
    (6, 'LES VIEILLES CHARRUES', 75, '2024-05-14', '2024-05-17', 'Pass 1 jour', 8, 3, 2),
    (7, 'NEKFEU', 30, '2024-06-27', '2024-06-27', 'ASSIS', 1, 1, 2),
    (8, 'ARTHUR H', 30, '2023-11-18', '2023-11-18', 'ASSIS', 5, 1, 2),
    (9, 'LE MALADE IMAGINAIRE', 9, '2023-11-12', '2023-11-12', 'RANG 1 Place 66', 2, 2, 2);


