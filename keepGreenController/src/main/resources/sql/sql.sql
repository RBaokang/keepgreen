create database keepgreen;
use keepgreen;

-- 创建用户账号表
CREATE TABLE user_account
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(64) UNIQUE NOT NULL,
    psw        VARCHAR(32)        NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建用户信息表
CREATE TABLE user_message
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    user_id      INT         NOT NULL,
    name         VARCHAR(20) NOT NULL,
    email        VARCHAR(64) NOT NULL,
    phone_number VARCHAR(16),
    birth_date   DATE,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (email) REFERENCES user_account (username)
);

-- 创建用户登录信息表
CREATE TABLE user_devices
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    username   varchar(64)  NOT NULL,
    device_id  VARCHAR(100) NOT NULL,
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (username) REFERENCES user_message (email)
);

insert into user_account(username, psw, created_at, updated_at) value (12345, 12345, time(now()), time(now()));
insert into user_message(name, email, birth_date, created_at, updated_at)
    value ('bk', 12345, 20040603, time(now()), time(now()));

commit;