alter table authors add column deleted boolean not null default false;
update authors set deleted = false;