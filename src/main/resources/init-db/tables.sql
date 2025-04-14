CREATE TABLE TB_STATUS (
    id integer PRIMARY KEY,
    code varchar(50) not null
);
CREATE TABLE TB_CONSENT (
    id VARCHAR(36) PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL,
    status_id integer NOT NULL,
    created_at TIMESTAMP NOT NULL,
    expires_at TIMESTAMP,
    additional_info varchar(50),
    foreign key (status_id) references TB_STATUS(id)
);

insert into TB_STATUS
select
1,
'ACTIVE'
WHERE
NOT EXISTS (SELECT 1 from TB_STATUS where code='ACTIVE');

insert into TB_STATUS
select
2,
'REVOKED'
WHERE
NOT EXISTS (SELECT 1 from TB_STATUS where code='REVOKED');

insert into TB_STATUS
select
3,
'EXPIRED'
WHERE
NOT EXISTS (SELECT 1 from TB_STATUS where code='EXPIRED');