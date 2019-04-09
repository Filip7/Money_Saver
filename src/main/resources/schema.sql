create table if not exists users
(
    id       identity           not null,
    username varchar(20) unique not null,
    password varchar(100)       not null,
    enabled  bit                not null
);

create table if not exists authorities
(
    username  varchar(20) not null,
    authority varchar(20) not null
);

create table if not exists wallet
(
    id           identity                 not null,
    sum          decimal(15, 2) default 0 not null,
    typeOfWallet varchar(255)             not null,
    createDate   timestamp                null,
    userId       bigint                   not null,
    foreign key (userId) references users (id)
);

create table if not exists expense
(
    id            identity       not null,
    name          varchar(255)   null,
    cost          decimal(15, 2) null,
    dateOfInsert  timestamp      null,
    typeOfExpense varchar(255)   null,
    walletId      bigint         null,
    foreign key (walletId) references wallet (id)
);

