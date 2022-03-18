-- Types
CREATE TYPE NAME_TYPE FROM VARCHAR(100);
CREATE TYPE AGE_TYPE FROM SMALLINT;

-- Table using types
CREATE TABLE Customers
(
  Id INTEGER NOT NULL,
  FirstName NAME_TYPE NOT NULL,
  LastName NAME_TYPE NOT NULL,
  Age AGE_TYPE,
  CONSTRAINT PK_Customers PRIMARY KEY (Id)
)
;