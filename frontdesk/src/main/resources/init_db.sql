INSERT INTO SCHEDULES(id, version, clinic_id) VALUES ('58915602-29f1-44bf-a787-cbb3f73c6e46'::UUID, 1,  1)

INSERT INTO DOCTORS (id, version, name) values (1L, 1, 'Dr. Jones')
INSERT INTO DOCTORS (id, version, name) values (2L, 1, 'Dr. Bernard')

INSERT INTO ROOMS (id, version, name) values (1L, 1, 'Room 1')
INSERT INTO ROOMS (id, version, name) values (2L, 1, 'Room 2')

INSERT INTO CLIENTS(id, version, name, salutation, email) VALUES(1L, 1, 'John Smit', 'Dear Mr', 'J.smit@email.com')
INSERT INTO CLIENTS(id, version, name, salutation, email) VALUES(2L, 1, 'Valerie Jones', 'Dear Mrs', 'mjones@email.com')

INSERT INTO PATIENTS(id, client_id, sex, name, species) VALUES(10L, 1L, 'Male', 'My Pet', 'buldog')
INSERT INTO PATIENTS(id, client_id, sex, name, species) VALUES(11L, 1L, 'Female', 'Doggy', 'st bernhard')

INSERT INTO APPOINTMENT_TYPES(id, version, minutes, code, name) VALUES(1, 1, 30, 'STD', 'Standard')

INSERT INTO APPOINTMENTS(id, version, schedule_id, appointment_type, title, client_id, patient_id, doctor_id, room_id, start_datetime, end_datetime, conflicting) VALUES ('c81fc3e3-08a4-44bd-80ba-5b219c9670df'::UUID, 1, '58915602-29f1-44bf-a787-cbb3f73c6e46'::UUID, 1, 'Appointment 1', 1, 10, 1, 1, '2024-05-29 13:30:00', '2024-05-29 14:00:00', false)
INSERT INTO APPOINTMENTS(id, version, schedule_id, appointment_type, title, client_id, patient_id, doctor_id, room_id, start_datetime, end_datetime, conflicting) VALUES ('5d30c617-3f51-4d6d-bcef-664b0ec0dbee'::UUID, 1, '58915602-29f1-44bf-a787-cbb3f73c6e46'::UUID, 1, 'Appointment 2', 1, 11, 1, 1, '2024-06-04 10:15:00', '2024-06-04 10:30:00', false)
