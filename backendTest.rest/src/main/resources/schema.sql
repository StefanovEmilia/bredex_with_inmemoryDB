/*DROP TABLE clients IF EXISTS;*/


CREATE TABLE IF NOT EXISTS clients (
  id INTEGER IDENTITY PRIMARY KEY,
  apiKey VARCHAR(155),
  name VARCHAR(100),
  email VARCHAR(100)
);
CREATE INDEX clients_id ON clients (id);

/*DROP TABLE positions IF EXISTS; */


CREATE TABLE IF NOT EXISTS positions (
  id INTEGER IDENTITY PRIMARY KEY,
  advertiser VARCHAR(155),
  roleName VARCHAR(100),
  location VARCHAR(100)
);
CREATE INDEX positions_id ON positions (id);