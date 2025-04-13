INSERT INTO ADDRESS (address_line1, address_line2, city, postal_code)
VALUES
    ('ul. Kwiatowa 15', 'Mieszkanie 7', 'Zabrze', '41-801'),
    ('Al. Jana Pawła II 32', 'Biuro 12B', 'Gliwice', '30-150'),
    ('Pl. Wolności 8', NULL, 'Katowice', '60-400'),
    ('ul. Leśna 21', 'Dom jednorodzinny', 'Rybnik', '80-180'),
    ('ul. Słoneczna 5A', NULL, 'Wrocław', '50-300');

INSERT INTO DOCTOR (first_name, last_name, telephone_number, email, doctor_number, specialization, address_id)
VALUES
    ('Maciej', 'Kowalewski', '123-456-789', 'maciej.kowalewski@example.com', 'DOC12345', 'CARDIOLOGIST', 1),
    ('Anna', 'Nowak', '987-654-321', 'anna.nowak@example.com', 'DOC67890', 'DERMATOLOGIST', 2),
    ('Piotr', 'Wiśniewski', '555-666-777', 'piotr.wisniewski@example.com', 'DOC11223', 'DERMATOLOGIST', 3),
    ('Ewa', 'Dąbrowska', '333-222-111', 'ewa.dabrowska@example.com', 'DOC44556', 'GP', 4),
    ('Marek', 'Zieliński', '777-888-999', 'marek.zielinski@example.com', 'DOC77889', 'DERMATOLOGIST', 5);

INSERT INTO PATIENT (first_name, last_name, telephone_number, email, patient_number, date_of_birth, marital_status, address_id)
VALUES
    ('Jan', 'Kowalski', '123-456-789', 'jan.kowalski@example.com', 'PAT12345', '1985-07-12', 'MARRIED', 1),
    ('Anna', 'Nowak', '987-654-321', 'anna.nowak@example.com', 'PAT67890', '1992-03-25', 'MARRIED', 2),
    ('Jan', 'Wiśniewski', '555-666-777', NULL, 'PAT11223', '1978-11-08', 'SINGLE', 3),
    ('Ewa', 'Dąbrowska', '333-222-111', 'ewa.dabrowska@example.com', 'PAT44556', '2000-06-30', 'DIVORCED', 4),
    ('Marek', 'Nowak', '777-888-999', 'marek.zielinski@example.com', 'PAT77889', '1995-09-15', 'WIDOWED', 5);

INSERT INTO VISIT (description, time, patient_id, doctor_id)
VALUES
    ('Rutynowa kontrola ciśnienia', '2024-03-10 09:00:00', 1, 1),
    ('Badanie dermatologiczne', '2024-03-11 11:30:00', 2, 2),
    ('Złamanie ręki - konsultacja', '2024-03-12 14:00:00', 3, 3),
    ('Szczepienie dziecięce', '2024-03-13 10:45:00', 4, 4),
    ('Bóle głowy - diagnoza', '2024-03-14 16:15:00', 5, 5),
    ('Badanie kontrolne serca', '2024-03-15 08:30:00', 1, 1),
    ('Pogorszenie wzroku', '2024-03-16 12:00:00', 2, 2),
    ('Fizjoterapia po operacji', '2024-03-17 15:30:00', 3, 3),
    ('Infekcja gardła', '2024-03-18 09:15:00', 4, 4),
    ('Example1','2024-03-15 08:30:00',1,1 ),
    ('Example2','2024-03-15 09:30:00',1,2 ),
    ('Problemy ze snem', '2024-03-19 17:00:00', 5, 5);

INSERT INTO MEDICAL_TREATMENT (description, type, visit_id)
VALUES
    ('Szyja', 'USG', 1),
    ('Migotanie przedsionków', 'EKG', 2),
    ('Staw kolanowy', 'RTG', 7),
    ('Jama brzuszna', 'USG', 9),
    ('Klatka piersiowa', 'RTG', 5);
