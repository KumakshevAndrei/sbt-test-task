-- Table: currency

-- DROP TABLE currency;

CREATE TABLE currency
(
  id serial NOT NULL,
  code character(10) NOT NULL,
  CONSTRAINT currency_pk PRIMARY KEY (id),
  CONSTRAINT code_unique UNIQUE (code)
  DEFERRABLE INITIALLY IMMEDIATE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE currency
  OWNER TO postgres;
