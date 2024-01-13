create table if not exists bank_account (
    id int primary key check (id > 0),
    owner_id int not null check (id > 0),
    balance int not null default 0,
    currency char(3) not null
);