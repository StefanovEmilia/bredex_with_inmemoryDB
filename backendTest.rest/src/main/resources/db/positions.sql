--DROP TABLE positions IF EXISTS

CREATE TABLE IF NOT EXISTS positions (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 8, INCREMENT BY 1) PRIMARY KEY,
  advertiser_apiKey VARCHAR(45),
  roleName VARCHAR(50),
  location VARCHAR(50)
);

--ALTER TABLE positions ADD CONSTRAINT fk_advertiser_apiKey FOREIGN KEY (advertiser_apiKey) REFERENCES clients (apiKey);