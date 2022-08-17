create database if not exists test2b1;
use test2b1;

create table if not exists bank
(
    `id`   bigint auto_increment primary key,
    `name` varchar(245) not null
);

create table if not exists currency
(
    `id`   bigint auto_increment primary key,
    `name` varchar(245) not null
);

create table if not exists `file`
(
    `id`           bigint auto_increment primary key,
    `name`         varchar(245) not null,
    `date_since`   date,
    `date_to`      date,
    `filling_time` datetime,
    `bank_id`      bigint       not null,
    `currency_id`  bigint       not null,
    CONSTRAINT `FK_File_Bank` FOREIGN KEY (`bank_id`)
        REFERENCES `bank` (`id`) ON DELETE CASCADE,
    CONSTRAINT `FK_File_Currency` FOREIGN KEY (`currency_id`)
        REFERENCES `currency` (`id`) ON DELETE CASCADE
);

create table if not exists class
(
    id          bigint auto_increment primary key,
    code        int          not null,
    description varchar(245) not null
);

create table if not exists balance_account
(
    id           bigint auto_increment primary key,
    balance_acc_numb varchar(245) not null,
    class_id bigint not null,
    CONSTRAINT `FK_Balance_Account_Class` FOREIGN KEY (`class_id`)
        REFERENCES `class` (`id`) ON DELETE CASCADE
);

create table if not exists incoming_balance
(
    id            bigint auto_increment primary key,
    active_amount       decimal,
    passive_amount        decimal
);

create table if not exists outgoing_balance
(
    id            bigint auto_increment primary key,
    active_amount       decimal,
    passive_amount        decimal
);

create table if not exists turnovers
(
    id            bigint auto_increment primary key,
    debit_amount       decimal,
    credit_amount        decimal
);

CREATE TABLE if not exists `record`
(
    id           bigint auto_increment primary key,
    file_id bigint NOT NULL,
    balance_acc_id   bigint NOT NULL,
    incoming_balance_id   bigint NOT NULL,
    outgoing_balance_id    bigint NOT NULL,
    turnovers_id       bigint   not null,
    constraint FK_Record_File foreign key (file_id) references file (id) ON DELETE cascade,
    constraint FK_Record_Balance_Acc foreign key (balance_acc_id) references balance_account (id) ON DELETE CASCADE,
    constraint FK_Record_Incoming_Balance foreign key (incoming_balance_id) references incoming_balance (id) ON DELETE CASCADE,
    constraint FK_Record_Outgoing_Balance foreign key (outgoing_balance_id) references outgoing_balance (id) ON DELETE CASCADE,
    constraint CK_Record_Turnovers foreign key (turnovers_id) references turnovers (id) ON DELETE CASCADE
);