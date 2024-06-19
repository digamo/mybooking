CREATE TABLE guest (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE property (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE booking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    details VARCHAR(255),
    guest_id BIGINT NOT NULL,
    property_id BIGINT NOT NULL,
    insert_at TIMESTAMP,
    update_at TIMESTAMP,
    FOREIGN KEY (guest_id) REFERENCES guest(id),
    FOREIGN KEY (property_id) REFERENCES property(id)
);

CREATE TABLE block (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    reason VARCHAR(255),
    property_id BIGINT NOT NULL,
    insert_at TIMESTAMP,
    update_at TIMESTAMP,
    FOREIGN KEY (property_id) REFERENCES property(id)
);

INSERT INTO guest (name) VALUES ('Diego Gomes');
INSERT INTO property (name) VALUES ('Test Property A');
INSERT INTO property (name) VALUES ('Test Property B');
