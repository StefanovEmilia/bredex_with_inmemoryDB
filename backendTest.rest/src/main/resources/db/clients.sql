DROP TABLE clients IF EXISTS

CREATE TABLE clients (
  apiKey VARCHAR(45) PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100)
);

