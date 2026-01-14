CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
CREATE TABLE category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    type varchar(50) NOT NULL
);
CREATE TABLE income (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    category_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    income_date DATE NOT NULL,
    description VARCHAR(255),

    CONSTRAINT fk_income_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT fk_income_category
        FOREIGN KEY (category_id)
        REFERENCES category(id)
);
CREATE TABLE expense (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    category_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    expense_date DATE NOT NULL,
    description VARCHAR(255),

    CONSTRAINT fk_expense_user  FOREIGN KEY (user_id) REFERENCES users(id),

    CONSTRAINT fk_expense_category FOREIGN KEY (category_id) REFERENCES category(id)
);
CREATE TABLE budget (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    category_id INT NOT NULL,
    month_year VARCHAR(7) NOT NULL, -- YYYY-MM
    amount DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_budget_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_budget_category
        FOREIGN KEY (category_id)
        REFERENCES category(id)
        ON DELETE CASCADE,

    UNIQUE (user_id, category_id, month_year)
);
