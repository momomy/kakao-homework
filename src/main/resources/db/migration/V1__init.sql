CREATE DATABASE IF NOT EXISTS `kakao_homework`;
USE `kakao_homework`;

DROP TABLE IF EXISTS `balance_transactions`;
CREATE TABLE `balance_transactions`
(
    `balance_transaction_id` BIGINT(20)     NOT NULL AUTO_INCREMENT,
    `user_id`                BIGINT(20)     NOT NULL,
    `transaction_type`       VARCHAR(64)    NOT NULL,
    `amount`                 DECIMAL(16, 2) NOT NULL,
    `reference_type`         VARCHAR(64)    NOT NULL,
    `created_at`             DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`             DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`balance_transaction_id`),
    KEY `balance_transactions_idx01` (`user_id`, `created_at`),
    KEY `balance_transactions_idx02` (`created_at`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='balance_transactions';

DROP TABLE IF EXISTS `balance_sprinkles`;
CREATE TABLE `balance_sprinkles`
(
    `balance_sprinkle_id`     BIGINT(20)     NOT NULL AUTO_INCREMENT,
    `token`                   VARCHAR(16)    NOT NULL,
    `user_id`                 BIGINT(20)     NOT NULL,
    `room_id`                 VARCHAR(128)   NOT NULL,
    `count`                   INTEGER        NOT NULL,
    `amount`                  DECIMAL(16, 2) NOT NULL,
    `expired_at`              DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `sprinkle_transaction_id` BIGINT(20)     NOT NULL,
    `refund_transaction_id`   BIGINT(20)     NULL,
    `status`                  VARCHAR(64)    NOT NULL,
    `created_at`              DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`              DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`balance_sprinkle_id`),
    KEY `balance_sprinkles_idx01` (`user_id`),
    KEY `balance_sprinkles_idx02` (`room_id`),
    KEY `balance_sprinkles_idx03` (`created_at`),
    KEY `balance_sprinkles_idx04` (`sprinkle_transaction_id`),
    KEY `balance_sprinkles_idx05` (`refund_transaction_id`),
    KEY `balance_sprinkles_idx06` (`status`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='balance_sprinkles';

DROP TABLE IF EXISTS `sprinkle_transactions`;
DROP TABLE IF EXISTS `balance_sprinkle_transactions`;
CREATE TABLE `balance_sprinkle_transactions`
(
    `balance_sprinkle_transaction_id` BIGINT(20)     NOT NULL AUTO_INCREMENT,
    `balance_sprinkle_id`             BIGINT(20)     NOT NULL,
    `user_id`                         BIGINT(20)     NOT NULL,
    `amount`                          DECIMAL(16, 2) NOT NULL,
    `balance_transaction_id`          BIGINT(29)     NOT NULL,
    `created_at`                      DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                      DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`balance_sprinkle_transaction_id`),
    KEY `sprinkle_transactions_idx01` (`balance_sprinkle_id`),
    KEY `sprinkle_transactions_idx02` (`created_at`),
    KEY `sprinkle_transactions_idx03` (`balance_transaction_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='balance_sprinkle_transactions';
