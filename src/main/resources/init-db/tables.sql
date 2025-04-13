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