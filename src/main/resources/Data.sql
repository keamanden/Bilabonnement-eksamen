-- Hardcoded data for the database

USE bilabbonnementdb;

INSERT INTO customer (first_name, last_name, email, phone, date_of_birth,
                      driver_license_no, street, house_no, postal_code, city, country)
VALUES ('Anna', 'Jensen', 'anna.jensen@example.com', '11112222', '1990-05-10',
        'DL10001', 'Nørregade', '10', '8000', 'Aarhus', 'Denmark'),

       ('Peter', 'Larsen', 'peter.larsen@example.com', '22223333', '1985-09-01',
        'DL10002', 'Vesterbro', '25A', '9000', 'Aalborg', 'Denmark'),

       ('Maria', 'Nielsen', 'maria.nielsen@example.com', '33334444', '1995-12-15',
        'DL10003', 'Østervej', '5', '2100', 'København', 'Denmark'),

       ('Jonas', 'Hansen', 'jonas.hansen@example.com', '44445555', '1988-03-22',
        'DL10004', 'Søndergade', '3B', '7100', 'Vejle', 'Denmark'),

       ('Louise', 'Madsen', 'louise.madsen@example.com', '55556666', '1992-07-30',
        'DL10005', 'Hovedgaden', '42', '8900', 'Randers', 'Denmark');


INSERT INTO employee (username, password, name, role)
VALUES ('test1', '123', 'Anders Jensen', 'Dataregistrering'),

       ('test2', '123', 'Mette Hansen', 'Skade_og_udbedring'),

       ('test3', '123', 'Thomas Knudsen', 'Forretningsudviklere');


INSERT INTO vehicle (registration_no, vin, brand, model, model_year)
VALUES ('AB12345', 'VIN0001', 'Toyota', 'Yaris', 2020),
       ('CD23456', 'VIN0002', 'VW', 'Golf', 2019),
       ('EF34567', 'VIN0003', 'BMW', '320i', 2021),
       ('GH45678', 'VIN0004', 'Audi', 'A3', 2018),
       ('IJ56789', 'VIN0005', 'Kia', 'Ceed', 2022),
       ('KL67890', 'VIN0006', 'Tesla', 'Model3', 2023),
       ('MN78901', 'VIN0007', 'Ford', 'Focus', 2017),
       ('OP89012', 'VIN0008', 'Hyundai', 'i30', 2020),
       ('QR90123', 'VIN0009', 'Opel', 'Corsa', 2019),
       ('ST01234', 'VIN0010', 'Skoda', 'Fabia', 2018);


-- add hardcoded data for leasecontract and damagereport here

/*
 INSERT INTO leasecontract (start_date, end_date, total_price, customer_id, vin, km_start
 ) VALUES
*/

/*
 INSERT INTO damagereport (damage_date, description, repair_cost, vin, employee_id, lease_id, km_slut
 ) VALUES
 */
