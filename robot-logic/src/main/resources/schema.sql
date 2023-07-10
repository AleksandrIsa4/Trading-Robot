DROP table IF EXISTS transactions cascade;
DROP table IF EXISTS position_instruments cascade;

CREATE TABLE IF NOT EXISTS transactions
(
    id                BIGINT       NOT NULL,
    class_code        VARCHAR(100) NOT NULL,
    sec_code          VARCHAR(100) NOT NULL,
    client_code       VARCHAR(100) NOT NULL,
    status            VARCHAR(100) NOT NULL,
    operation_type    VARCHAR(100) NOT NULL,
    price             FLOAT        NOT NULL,
    quantity          BIGINT       NOT NULL,
    quantity_complete BIGINT       NOT NULL,
    CONSTRAINT pk_transactions PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS position_instruments
(
    class_code    VARCHAR(100)        NOT NULL,
    sec_code      VARCHAR(100) UNIQUE NOT NULL,
    quantity      BIGINT              NOT NULL,
    average_price FLOAT               NOT NULL,
    CONSTRAINT pk_position_instruments PRIMARY KEY (sec_code)
);

CREATE TABLE IF NOT EXISTS account
(
    class_code  VARCHAR(100) NOT NULL,
    client_code VARCHAR(100),
    account     VARCHAR(100) NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (account)
);

CREATE TABLE IF NOT EXISTS trade_akzii
(
    sec_code        VARCHAR(100) UNIQUE NOT NULL,
    buy_price       FLOAT               NOT NULL,
    quantity_first  BIGINT              NOT NULL,
    quantity_second BIGINT              NOT NULL,
    CONSTRAINT pk_trade_akzii PRIMARY KEY (sec_code)
    --  CONSTRAINT fk_compilation_id_trade_akzii FOREIGN KEY (sec_code) REFERENCES position_instruments (sec_code)
);