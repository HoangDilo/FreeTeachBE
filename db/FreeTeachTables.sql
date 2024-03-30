CREATE TABLE user_account (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  age INTEGER,
  email VARCHAR(255) UNIQUE NOT NULL,
  avatarURL VARCHAR(255),
  username VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  money INTEGER DEFAULT 0
);

CREATE TABLE student (
  id INTEGER REFERENCES user_account(id) PRIMARY KEY,
  grade INTEGER
);

CREATE TABLE Teacher (
  id INTEGER REFERENCES user_account(id) PRIMARY KEY,
  pricePerHour INTEGER,
  avaragePoint DECIMAL(10,2),
  description TEXT,
  activeTimeStart TIMESTAMP,
  activeTimeEnd TIMESTAMP
);



