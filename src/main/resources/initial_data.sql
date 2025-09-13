-- LOCATIONS
INSERT INTO locations (id, name, city, address) VALUES
 (1, 'Hala Expo', 'Warszawa', 'Przykładowa 1'),
 (2, 'Centrum Kultury', 'Kraków', 'Rynek 2'),
 (3, 'Strefa Sztuki', 'Gdańsk', 'Długa 10');

-- PARTICIPANTS
INSERT INTO participants (id, name, email) VALUES
 (1, 'Jan Kowalski', 'jan.kowalski@example.com'),
 (2, 'Anna Nowak', 'anna.nowak@example.com'),
 (3, 'Piotr Zieliński', 'piotr.zielinski@example.com'),
 (4, 'Maria Wiśniewska', 'maria.wisniewska@example.com');

-- EVENTS
INSERT INTO events (id, name, description, date, capacity, location_id) VALUES
 (1, 'Koncert jesienny', 'Muzyka filmowa na żywo',        '2025-10-01 18:00:00', 150, 1),
 (2, 'Warsztaty Java',  'Spring Boot dla początkujących', '2025-10-05 10:00:00',  40, 2),
 (3, 'Spotkanie UX',    'Badania użytkowników w praktyce','2025-10-12 17:30:00',  60, 3);

-- REGISTRATIONS
INSERT INTO registrations (id, event_id, participant_id, registration_date) VALUES
 (1, 1, 1, '2025-09-20 09:00:00'),
 (2, 1, 2, '2025-09-20 09:05:00'),
 (3, 2, 3, '2025-09-21 14:00:00'),
 (4, 3, 4, '2025-09-22 12:30:00');