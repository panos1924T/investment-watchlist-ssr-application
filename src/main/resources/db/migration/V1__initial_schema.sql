CREATE TABLE firms (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,

    CONSTRAINT pk_firms PRIMARY KEY (id),
    CONSTRAINT uk_firms_name UNIQUE (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE analysts (
    id BIGINT NOT NULL AUTO_INCREMENT,
    uuid BINARY(16) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    email VARCHAR(30) NOT NULL,
    firm_id BIGINT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,

    CONSTRAINT pk_analysts PRIMARY KEY (id),
    CONSTRAINT uk_analysts_uuid UNIQUE (uuid),
    CONSTRAINT uk_analysts_email UNIQUE (email),
    CONSTRAINT fk_analysts_firms FOREIGN KEY (firm_id)
        REFERENCES firms(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    INDEX idx_analysts_firm_id (firm_id),
    INDEX idx_analysts_lastname (lastname)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;