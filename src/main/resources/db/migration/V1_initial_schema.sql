CREATE TABLE assets (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        uuid VARCHAR(36) NOT NULL UNIQUE,
                        ticker VARCHAR(10) NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        current_price DECIMAL(18, 2),
                        target_price DECIMAL(18, 2),
                        risk_level VARCHAR(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;