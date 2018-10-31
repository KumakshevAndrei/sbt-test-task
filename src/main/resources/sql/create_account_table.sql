-- Table: account

-- DROP TABLE account;

CREATE TABLE account
(
  id serial NOT NULL,
  account_number bigint NOT NULL,
  balance double precision NOT NULL DEFAULT 0,
  currency integer NOT NULL,
  CONSTRAINT account_pk PRIMARY KEY (id),
  CONSTRAINT currency_fk FOREIGN KEY (currency)
      REFERENCES currency (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT account_number_unique UNIQUE (account_number)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE account
  OWNER TO postgres;
