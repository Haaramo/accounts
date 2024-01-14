create table if not exists bank_account
(
    id       int primary key check (id > 0),
    owner_id int     not null check (id > 0),
    balance  int     not null default 0,
    currency char(3) not null
);

create table if not exists bank_transaction
(
    id              serial primary key check (id > 0),
    bank_account_id int not null check (bank_account_id > 0),
    amount          int not null check (amount != 0),
    time            timestamptz default now(),

    constraint fk_bank_account_id
        foreign key (bank_account_id) references bank_account (id)
);



