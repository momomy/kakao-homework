CREATE DATABASE IF NOT EXISTS `kakao_homework`;
USE `kakao_homework`;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `user_id`    BIGINT(20) NOT NULL AUTO_INCREMENT,
    `xid`        bigint(20) NOT NULL,
    `created_at` DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`),
    KEY `users_idx_01` (`xid`),
    KEY `users_idx_02` (`created_at`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='users';


DROP TABLE IF EXISTS `rooms`;
CREATE TABLE `rooms`
(
    `room_id`    BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `xid`        VARCHAR(128) NOT NULL,
    `created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`room_id`),
    KEY `rooms_idx_01` (`xid`),
    KEY `rooms_idx_02` (`created_at`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='rooms';

DROP TABLE IF EXISTS `room_users`;
CREATE TABLE `room_users`
(
    `room_user_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `room_id`      BIGINT(20) NOT NULL,
    `user_id`      BIGINT(20) NOT NULL,
    `created_at`   DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`   DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`room_user_id`),
    KEY `room_users_idx01` (`room_id`),
    KEY `room_users_idx02` (`user_id`),
    KEY `room_users_idx03` (`created_at`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='room_users';

DROP TABLE IF EXISTS `balance_transactions`;
CREATE TABLE `balance_transactions`
(
    `balance_transaction_id` BIGINT(20)     NOT NULL AUTO_INCREMENT,
    `user_id`                BIGINT(20)     NOT NULL,
    `transaction_type`       VARCHAR(64)    NOT NULL,
    `amount`                 DECIMAL(16, 2) NOT NULL,
    `reference_user_id`      BIGINT(20)     NOT NULL,
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
    `balance_sprinkle_id` BIGINT(20)     NOT NULL AUTO_INCREMENT,
    `token`               VARCHAR(16)    NOT NULL,
    `user_id`             BIGINT(20)     NOT NULL,
    `room_id`             BIGINT(20)     NOT NULL,
    `count`               INTEGER        NOT NULL,
    `amount`              DECIMAL(16, 2) NOT NULL,
    `expired_at`          DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_at`          DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`          DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`balance_sprinkle_id`),
    KEY `balance_sprinkles_idx01` (`user_id`),
    KEY `balance_sprinkles_idx02` (`room_id`),
    KEY `balance_sprinkles_idx03` (`created_at`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='balance_sprinkles';

DROP TABLE IF EXISTS `sprinkle_transactions`;
CREATE TABLE `sprinkle_transactions`
(
    `sprinkle_transaction_id` BIGINT(20)     NOT NULL AUTO_INCREMENT,
    `balance_sprinkle_id`     BIGINT(20)     NOT NULL,
    `user_id`                 BIGINT(20)     NOT NULL,
    `amount`                  DECIMAL(16, 2) NOT NULL,
    `created_at`              DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`              DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`sprinkle_transaction_id`),
    KEY `sprinkle_transactions_idx01` (`balance_sprinkle_id`),
    KEY `sprinkle_transactions_idx02` (`created_at`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='balance_sprinkles';
