DROP table IF EXISTS transactions cascade;
DROP table IF EXISTS position_instruments cascade;
DROP table IF EXISTS information_account cascade;
DROP table IF EXISTS trade_akzii cascade;

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
    class_code     VARCHAR(100)        NOT NULL,
    sec_code       VARCHAR(100) UNIQUE NOT NULL,
    quantity       BIGINT              NOT NULL,
    average_price  FLOAT               NOT NULL,
    lot_size       BIGINT              NOT NULL,
    sec_price_step FLOAT               NOT NULL,
    CONSTRAINT pk_position_instruments PRIMARY KEY (sec_code)
);

CREATE TABLE IF NOT EXISTS information_account
(
    class_code  VARCHAR(100) NOT NULL,
    client_code VARCHAR(100),
    account     VARCHAR(100) NOT NULL,
    firm_id     VARCHAR(100) NOT NULL,
    tag_money   VARCHAR(100) NOT NULL,
    money       FLOAT,
    CONSTRAINT pk_account PRIMARY KEY (account)
);

CREATE TABLE IF NOT EXISTS trade_akzii
(
    sec_code        VARCHAR(100) UNIQUE NOT NULL,
    buy_price       FLOAT               NOT NULL,
    quantity_first  BIGINT              NOT NULL,
    quantity_second BIGINT              NOT NULL,
    CONSTRAINT pk_trade_akzii PRIMARY KEY (sec_code)
    -- CONSTRAINT fk_sec_code_trade_akzii FOREIGN KEY (sec_code) REFERENCES position_instruments (sec_code)
);

CREATE TABLE IF NOT EXISTS information_tool
(
    id          BIGINT       NOT NULL,
    sec_code    VARCHAR(100) NOT NULL,
    class_code  VARCHAR(100) NOT NULL,
    bid         FLOAT        NOT NULL,
    biddepth    FLOAT        NOT NULL,
    biddeptht   FLOAT        NOT NULL,
    offer       FLOAT        NOT NULL,
    offerdepth  FLOAT        NOT NULL,
    offerdeptht FLOAT        NOT NULL,
    last        FLOAT        NOT NULL,
    open        FLOAT        NOT NULL,
    CONSTRAINT pk_information_tool PRIMARY KEY (id)
);


