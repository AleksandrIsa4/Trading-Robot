DROP table IF EXISTS transactions cascade;

CREATE TABLE IF NOT EXISTS transactions
(
    id                BIGINT,
    class_code        VARCHAR(512),
    sec_code          VARCHAR(512),
    client_code       VARCHAR(512),
    status            VARCHAR(512),
    operation_type    VARCHAR(512),
    price             FLOAT,
    quantity          BIGINT,
    quantity_complete BIGINT,
    CONSTRAINT pk_transactions PRIMARY KEY (id)
);