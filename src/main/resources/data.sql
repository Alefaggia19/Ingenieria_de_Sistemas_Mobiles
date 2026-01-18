INSERT INTO USERS (realname,password,email)
VALUES
('admin','$2a$12$Akz1PcXyaQhFpTEWSF2A6e92PxYiPrfOihVz/NVpiteGQWJO3OQ8K','admin@checkit.com');

INSERT INTO users (realname, email, password) VALUES ('Ana Gómez', 'ana@example.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.TVuHOnu');

-- 1. Inserimento Users (Password: "password" codificata BCrypt)
INSERT INTO users (realname, email, password) VALUES ('user1', 'user1@test.com', '$2a$10$8.UnVuG9UMJpm39zS..G2u4.O9.p.t6S1U2V.E6t7m3.1.2.3.4.5');
INSERT INTO users (realname, email, password) VALUES ('Marco Rossi', 'marco@test.it', '$2a$10$8.UnVuG9UMJpm39zS..G2u4.O9.p.t6S1U2V.E6t7m3.1.2.3.4.5');
INSERT INTO users (realname, email, password) VALUES ('Lucas Garcia', 'lucas@test.es', '$2a$10$8.UnVuG9UMJpm39zS..G2u4.O9.p.t6S1U2V.E6t7m3.1.2.3.4.5');
INSERT INTO users (realname, email, password) VALUES ('Sofia Bianchi', 'sofia@test.it', '$2a$10$8.UnVuG9UMJpm39zS..G2u4.O9.p.t6S1U2V.E6t7m3.1.2.3.4.5');
INSERT INTO users (realname, email, password) VALUES ('Test User', 'test@checkit.com', '$2a$10$8.UnVuG9UMJpm39zS..G2u4.O9.p.t6S1U2V.E6t7m3.1.2.3.4.5');

-- 2. Inserimento Sessioni (KPI: Tasa de Conversión)
-- Insert 4 session 
INSERT INTO user_sessions (user_id, start_time) VALUES (1, CURRENT_TIMESTAMP);
INSERT INTO user_sessions (user_id, start_time) VALUES (1, CURRENT_TIMESTAMP);
INSERT INTO user_sessions (user_id, start_time) VALUES (2, CURRENT_TIMESTAMP);
INSERT INTO user_sessions (user_id, start_time) VALUES (2, CURRENT_TIMESTAMP);
INSERT INTO user_sessions (user_id, start_time) VALUES (1, CURRENT_TIMESTAMP);
INSERT INTO user_sessions (user_id, start_time) VALUES (1, CURRENT_TIMESTAMP);
INSERT INTO user_sessions (user_id, start_time) VALUES (2, CURRENT_TIMESTAMP);
INSERT INTO user_sessions (user_id, start_time) VALUES (3, CURRENT_TIMESTAMP);
INSERT INTO user_sessions (user_id, start_time) VALUES (4, CURRENT_TIMESTAMP);
INSERT INTO user_sessions (user_id, start_time) VALUES (1, DATEADD('DAY', -2, CURRENT_TIMESTAMP));
INSERT INTO user_sessions (user_id, start_time) VALUES (2, DATEADD('DAY', -3, CURRENT_TIMESTAMP));
INSERT INTO user_sessions (user_id, start_time) VALUES (3, DATEADD('DAY', -4, CURRENT_TIMESTAMP));
INSERT INTO user_sessions (user_id, start_time) VALUES (4, DATEADD('DAY', -8, CURRENT_TIMESTAMP)); -- Fuori da questa settimana
INSERT INTO user_sessions (user_id, start_time) VALUES (1, CURRENT_TIMESTAMP);
INSERT INTO user_sessions (user_id, start_time) VALUES (2, CURRENT_TIMESTAMP);
INSERT INTO user_sessions (user_id, start_time) VALUES (3, CURRENT_TIMESTAMP);

-- 3. Inserimento Sfide (KPI: Número de desafíos creados / Conversiones)
-- Se abbiamo 2 sfide su 4 sessioni, la Tasa de Conversión sarà 50%
INSERT INTO challenges (name, description, is_ordered, user_id, creation_date) 
VALUES ('Ruta Romana', 'Descrizione test', true, 1, CURRENT_TIMESTAMP);
INSERT INTO challenges (name, description, is_ordered, user_id, creation_date) 
VALUES ('Misteri di Cordoba', 'Descrizione test', false, 2, CURRENT_TIMESTAMP);
INSERT INTO challenges (name, description, is_ordered, user_id, creation_date) 
VALUES ('Tour Mezquita', 'Descubre la historia de la Mezquita', true, 1, CURRENT_TIMESTAMP);
INSERT INTO challenges (name, description, is_ordered, user_id, creation_date) 
VALUES ('Misterios Judería', 'Cerca i segreti del quartiere ebraico', false, 2, CURRENT_TIMESTAMP);
INSERT INTO challenges (name, description, is_ordered, user_id, creation_date) 
VALUES ('Patios de Córdoba', 'Visita i cortili più belli', true, 3, DATEADD('DAY', -2, CURRENT_TIMESTAMP));
INSERT INTO challenges (name, description, is_ordered, user_id, creation_date) 
VALUES ('Desafío Antiguo', 'Sfida creata tempo fa', false, 1, DATEADD('DAY', -15, CURRENT_TIMESTAMP));

--- 3. TASKS -----
-- Task por challenge 1 (Ruta Romana)
INSERT INTO tasks (name, type, task_order, challenge_id,qr_answer) VALUES ('Puerta del Puente', 'QR', 1, 1,'qr');
INSERT INTO tasks (name, type, task_order, challenge_id,text_answer) VALUES ('Templo Romano', 'TEXT', 2, 1,'text');

-- Task por challenge 2 (Misteri di Cordoba)
INSERT INTO tasks (name, type, task_order, challenge_id,nfc_answer) VALUES ('La Facultad de Filosofía', 'NFC', 1, 2,'nfc');
INSERT INTO tasks (name, type, task_order, challenge_id,text_answer) VALUES ('Callejón del Pañuelo', 'TEXT', 2, 2,'text');

-- Task por challenge 3 (Tour Mezquita)
INSERT INTO tasks (name, type, task_order, challenge_id,qr_answer) VALUES ('Patio de los Naranjos', 'QR', 1, 3,'qr');
INSERT INTO tasks (name, type, task_order, challenge_id,nfc_answer) VALUES ('El Mihrab', 'NFC', 2, 3,'nfc');

-- Task por challenge 4 (Misterios Judería)
INSERT INTO tasks (name, type, task_order, challenge_id,qr_answer) VALUES ('Estatua de Maimónides', 'QR', 1, 4,'qr');
INSERT INTO tasks (name, type, task_order, challenge_id,text_answer) VALUES ('Sinagoga de Córdoba', 'TEXT', 2, 4,'text');

-- Task por challenge 5 (Patios de Córdoba)
INSERT INTO tasks (name, type, task_order, challenge_id,nfc_answer) VALUES ('Palacio de Viana', 'NFC', 1, 5,'nfc');
INSERT INTO tasks (name, type, task_order, challenge_id,qr_answer) VALUES ('Patio de San Basilio', 'QR', 2, 5,'qr');

-- Task por challenge 6 (Desafío Antiguo)
INSERT INTO tasks (name, type, task_order, challenge_id,text_answer) VALUES ('Vieja Inscripción', 'TEXT', 1, 6,'text');
INSERT INTO tasks (name, type, task_order, challenge_id,qr_answer) VALUES ('Puerta de Almodóvar', 'QR', 2, 6,'qr');

-- 4. Inserimento NPS (KPI: Net Promoter Score)
INSERT INTO nps_ratings (score, user_id, timestamp) VALUES (10, 1, CURRENT_TIMESTAMP);
INSERT INTO nps_ratings (score, user_id, timestamp) VALUES (8.0, 2, CURRENT_TIMESTAMP);
INSERT INTO nps_ratings (score, user_id, timestamp) VALUES (6.0, 1, CURRENT_TIMESTAMP);
INSERT INTO nps_ratings (score, user_id, timestamp) VALUES (10, 1, CURRENT_TIMESTAMP);
INSERT INTO nps_ratings (score, user_id, timestamp) VALUES (9.0, 2, CURRENT_TIMESTAMP);
INSERT INTO nps_ratings (score, user_id, timestamp) VALUES (2.0, 3, CURRENT_TIMESTAMP); -- Un utente insoddisfatto
INSERT INTO nps_ratings (score, user_id, timestamp) VALUES (8.0, 4, CURRENT_TIMESTAMP);
INSERT INTO nps_ratings (score, user_id, timestamp) VALUES (7.0, 1, CURRENT_TIMESTAMP);

-- 5. TASK COMPLETATI (KPI: Tareas completadas totales por día)
-- Ipotizziamo che esistano già dei task con ID 1, 2, 3
INSERT INTO task_completions (task_id, user_id, completed_at) VALUES (1, 1, CURRENT_TIMESTAMP);
INSERT INTO task_completions (task_id, user_id, completed_at) VALUES (2, 1, CURRENT_TIMESTAMP);
INSERT INTO task_completions (task_id, user_id, completed_at) VALUES (1, 2, CURRENT_TIMESTAMP);

-- Añadimos pistas a la tarea 1
INSERT INTO clues (task_id,text_clue ) VALUES (3,'¿Alguien te está observando?');
INSERT INTO clues (task_id,text_clue ) VALUES (3,'Siempre desconfia de los sotanos');

INSERT INTO clues (task_id,text_clue ) VALUES (4,'Siempre mira a tu espalda');

