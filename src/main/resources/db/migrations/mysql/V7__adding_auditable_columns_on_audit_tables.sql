ALTER TABLE `user_aud`
    ADD COLUMN `created_by`         varchar(255) DEFAULT NULL,
    ADD COLUMN `created_date`       timestamp    DEFAULT NULL,
    ADD COLUMN `last_modified_by`   varchar(255) DEFAULT NULL,
    ADD COLUMN `last_modified_date` timestamp    DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE `task_aud`
    ADD COLUMN `created_by`         varchar(255) DEFAULT NULL,
    ADD COLUMN `created_date`       timestamp    DEFAULT NULL,
    ADD COLUMN `last_modified_by`   varchar(255) DEFAULT NULL,
    ADD COLUMN `last_modified_date` timestamp    DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP;