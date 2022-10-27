DROP TABLE clients IF EXISTS;
DROP TABLE positions IF EXISTS;


CREATE TABLE clients (
  apiKey VARCHAR(45) IDENTITY PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100)
);
CREATE INDEX clients_name ON admins (name);


CREATE TABLE positions (
  id INTEGER IDENTITY PRIMARY KEY,
  advertiser_apiKey VARCHAR(45),
  roleName VARCHAR(50),
  location VARCHAR(50)
);
CREATE INDEX positions_roleName ON salesmans (roleName);

ALTER TABLE positions ADD CONSTRAINT fk_advertiser_apiKey FOREIGN KEY (advertiser_apiKey) REFERENCES salesmans (apiKey);