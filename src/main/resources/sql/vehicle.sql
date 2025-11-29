Use bilabbonnementdb;

DROP TABLE IF EXISTS Vehicle;
CREATE TABLE Vehicle (
                         vin_id VARCHAR(30) PRIMARY KEY NOT NULL UNIQUE,
                         registration_nr VARCHAR(30) NOT NULL UNIQUE,
                         brand VARCHAR(10) NOT NULL,
                         model VARCHAR(10) NOT NULL,
                         model_year int(10) NOT NULL
);