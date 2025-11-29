Use bilabbonnementdb;



DROP TABLE IF EXISTS LeaseContract;
CREATE TABLE LeaseContract (
                               lease_id INT AUTO_INCREMENT PRIMARY KEY,
                               start_date DATE NOT NULL,
                               end_date DATE NOT NULL,
                               total_price DOUBLE(10, 2) NOT NULL,
                               customer_id INT(10),
                               vin_id VARCHAR(30) NOT NULL,
                               FOREIGN KEY (vin_id) REFERENCES Vehicle (vin_id)

);
