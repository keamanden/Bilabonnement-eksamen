Use bilabbonnementdb;

DROP TABLE IF EXISTS DamageReport;
CREATE TABLE DamageReport (
                              damage_id INT (10) PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,
                              damage_date DATE
)