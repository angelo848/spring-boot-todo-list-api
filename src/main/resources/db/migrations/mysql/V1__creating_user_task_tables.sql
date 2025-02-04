create table `user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `email` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `uk_email` UNIQUE (`email`)
);

create table `task` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `description` varchar(255) NOT NULL,
    `status` varchar(50) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_task_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);