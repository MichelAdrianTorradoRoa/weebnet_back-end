CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    fullname VARCHAR(100) NOT NULL,
    cellphone VARCHAR(15),
    email VARCHAR(100) NOT NULL UNIQUE,
    birth_day DATE NOT NULL,
    password TEXT NOT NULL,
    bio TEXT,
    photo VARCHAR(255)
);

CREATE TABLE type_interactions (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE publications (
    id SERIAL PRIMARY KEY,
    text VARCHAR(500) NOT NULL,
    photo VARCHAR(255),
    id_user INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE publication_tags (
    id SERIAL PRIMARY KEY,
    id_publication INT NOT NULL,
    tag VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_publication) REFERENCES publications(id) ON DELETE CASCADE
);

CREATE TABLE follows (
    id SERIAL PRIMARY KEY,
    id_follower INT NOT NULL,
    id_following INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_follower) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (id_following) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE notifications (
    id SERIAL PRIMARY KEY,
    type INT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_giver INT NOT NULL,
    id_receiver INT NOT NULL,
    FOREIGN KEY (type) REFERENCES type_interactions(id),
    FOREIGN KEY (id_giver) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (id_receiver) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE likes (
    id SERIAL PRIMARY KEY,
    id_publication INT NOT NULL,
    id_user INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_publication) REFERENCES publications(id) ON DELETE CASCADE,
    FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    text VARCHAR(200) NOT NULL,
    id_publication INT NOT NULL,
    id_user INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_publication) REFERENCES publications(id) ON DELETE CASCADE,
    FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE
);


ALTER TABLE follows 
ADD CONSTRAINT check_no_self_follow 
CHECK (id_follower <> id_following);

ALTER TABLE likes 
ADD CONSTRAINT unique_like UNIQUE (id_publication, id_user);

ALTER TABLE notifications 
ADD CONSTRAINT fk_notification_type 
FOREIGN KEY (type) REFERENCES type_interactions(id) ON DELETE RESTRICT;

ALTER TABLE follows 
ADD CONSTRAINT unique_follow UNIQUE (id_follower, id_following);

ALTER TABLE comments 
ADD CONSTRAINT check_non_empty_comment 
CHECK (LENGTH(TRIM(text)) > 0);


ALTER TABLE publications 
ADD CONSTRAINT check_non_empty_publication 
CHECK (LENGTH(TRIM(text)) > 0);


ALTER TABLE notifications 
ALTER COLUMN type SET NOT NULL;


CREATE OR REPLACE FUNCTION validate_user_age() 
RETURNS TRIGGER AS $$
BEGIN
    IF (EXTRACT(YEAR FROM AGE(NEW.birth_day)) < 14) THEN
        RAISE EXCEPTION 'You must be at least 14 years old to register.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_validate_user_age
BEFORE INSERT ON users
FOR EACH ROW
EXECUTE FUNCTION validate_user_age();

INSERT INTO type_interactions (type) VALUES
('like'),
('comment'),
('follow'),
('mention');

ALTER TABLE users
ALTER COLUMN password TYPE varchar(60);

select * from users;