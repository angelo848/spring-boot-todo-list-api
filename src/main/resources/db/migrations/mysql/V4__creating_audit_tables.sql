CREATE TABLE `UserEntity_AUD` (
    `id`       bigint(20) NOT NULL,
    `name`     varchar(255) DEFAULT NULL,
    `email`    varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `REV`      bigint(20) NOT NULL,
    `REVTYPE`  tinyint(3) NOT NULL,
    PRIMARY KEY (`id`, `REV`),
    INDEX (`REV`)
);

CREATE TABLE `TaskEntity_AUD` (
    `id`          bigint(20) NOT NULL,
    `description` varchar(255) DEFAULT NULL,
    `status`      varchar(255) DEFAULT NULL,
    `user_id`     bigint(20)   DEFAULT NULL,
    `REV`         bigint(20) NOT NULL,
    `REVTYPE`     tinyint(3) NOT NULL,
    PRIMARY KEY (`id`, `REV`),
    INDEX (`REV`)
);