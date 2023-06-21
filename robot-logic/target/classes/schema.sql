DROP table IF EXISTS transactions cascade;

CREATE TABLE IF NOT EXISTS transactions
(
    id               BIGINT,
    classCode        VARCHAR(512),
    secCode          VARCHAR(512),
    clientCode       VARCHAR(512),
    status           VARCHAR(512),
    operationType    VARCHAR(512),
    price            FLOAT,
    quantity         BIGINT,
    quantityComplete BIGINT,
    CONSTRAINT pk_transactions PRIMARY KEY (id)
);

