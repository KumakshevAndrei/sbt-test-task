-- Table: account_operation

-- DROP TABLE account_operation;

CREATE TABLE account_operation
(
  id serial NOT NULL,
  operation_timestamp timestamp with time zone NOT NULL,
  target integer,
  sum double precision NOT NULL,
  source integer NOT NULL,
  CONSTRAINT account_operation_pk PRIMARY KEY (id),
  CONSTRAINT receiver_fk FOREIGN KEY (target)
      REFERENCES account (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT target_fk FOREIGN KEY (source)
      REFERENCES account (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE account_operation
  OWNER TO postgres;
