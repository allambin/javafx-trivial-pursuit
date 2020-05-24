create table cards (
    id int not null AUTO_INCREMENT,
    question text,
    answer text,
    is_published tinyint not null default 0,
    type_id int not null,
    PRIMARY KEY (id),
    CONSTRAINT fk_card_type
        FOREIGN KEY (type_id)
            REFERENCES card_types(id)
            ON UPDATE CASCADE
            ON DELETE RESTRICT
) CHARACTER SET utf8mb4;
