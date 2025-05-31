-- Insert Publishers
INSERT INTO publisher (name, address) VALUES
('Penguin Random House', '1745 Broadway, New York, NY 10019'),
('HarperCollins Publishers', '195 Broadway, New York, NY 10007'),
('Simon & Schuster', '1230 Avenue of the Americas, New York, NY 10020'),
('Macmillan Publishers', '120 Broadway, New York, NY 10271'),
('Hachette Book Group', '1290 Avenue of the Americas, New York, NY 10104');

-- Insert Authors
INSERT INTO author (first_name, last_name) VALUES
('J.K.', 'Rowling'),
('George', 'Orwell'),
('Harper', 'Lee'),
('F. Scott', 'Fitzgerald'),
('Jane', 'Austen'),
('Mark', 'Twain'),
('Charles', 'Dickens'),
('William', 'Shakespeare'),
('Agatha', 'Christie'),
('Stephen', 'King'),
('Margaret', 'Atwood'),
('Toni', 'Morrison'),
('Ernest', 'Hemingway'),
('Virginia', 'Woolf'),
('Gabriel', 'García Márquez');

-- Insert Books
INSERT INTO book (title, isbn, publication_date, publisher_id) VALUES
('Harry Potter and the Philosopher''s Stone', '978-0-7475-3269-9', '1997-06-26', 1),
('1984', '978-0-452-28423-4', '1949-06-08', 2),
('To Kill a Mockingbird', '978-0-06-112008-4', '1960-07-11', 2),
('The Great Gatsby', '978-0-7432-7356-5', '1925-04-10', 3),
('Pride and Prejudice', '978-0-14-143951-8', '1813-01-28', 1),
('The Adventures of Tom Sawyer', '978-0-486-40077-6', '1876-12-01', 4),
('Great Expectations', '978-0-14-143956-3', '1861-08-01', 1),
('Romeo and Juliet', '978-0-7434-7712-3', '1597-01-01', 3),
('Murder on the Orient Express', '978-0-06-207350-4', '1934-01-01', 2),
('The Shining', '978-0-385-12167-5', '1977-01-28', 1),
('The Handmaid''s Tale', '978-0-385-49081-8', '1985-08-01', 5),
('Beloved', '978-1-4000-3341-6', '1987-09-01', 4),
('The Old Man and the Sea', '978-0-684-80122-3', '1952-09-01', 3),
('Mrs. Dalloway', '978-0-15-662870-9', '1925-05-14', 5),
('One Hundred Years of Solitude', '978-0-06-088328-7', '1967-06-05', 2),
('Animal Farm', '978-0-452-28424-1', '1945-08-17', 2),
('Hamlet', '978-0-7434-7712-4', '1603-01-01', 3),
('A Tale of Two Cities', '978-0-14-143960-0', '1859-11-26', 1),
('It', '978-0-670-81302-4', '1986-09-15', 1),
('The Cat in the Hat', '978-0-394-80001-1', '1957-03-12', 4);

-- Insert Book-Author relationships (many-to-many)
INSERT INTO book_author (book_id, author_id) VALUES
(1, 1),   -- Harry Potter - J.K. Rowling
(2, 2),   -- 1984 - George Orwell
(3, 3),   -- To Kill a Mockingbird - Harper Lee
(4, 4),   -- The Great Gatsby - F. Scott Fitzgerald
(5, 5),   -- Pride and Prejudice - Jane Austen
(6, 6),   -- Tom Sawyer - Mark Twain
(7, 7),   -- Great Expectations - Charles Dickens
(8, 8),   -- Romeo and Juliet - William Shakespeare
(9, 9),   -- Murder on the Orient Express - Agatha Christie
(10, 10), -- The Shining - Stephen King
(11, 11), -- The Handmaid's Tale - Margaret Atwood
(12, 12), -- Beloved - Toni Morrison
(13, 13), -- The Old Man and the Sea - Ernest Hemingway
(14, 14), -- Mrs. Dalloway - Virginia Woolf
(15, 15), -- One Hundred Years of Solitude - Gabriel García Márquez
(16, 2),  -- Animal Farm - George Orwell
(17, 8),  -- Hamlet - William Shakespeare
(18, 7),  -- A Tale of Two Cities - Charles Dickens
(19, 10), -- It - Stephen King
(20, 1);  -- The Cat in the Hat - J.K. Rowling (fictional collaboration)

-- Insert Users
INSERT INTO users (username, email, password) VALUES
('john_doe', 'john.doe@email.com', 'password123'),
('jane_smith', 'jane.smith@email.com', 'securepass456'),
('mike_johnson', 'mike.johnson@email.com', 'mypassword789'),
('sarah_wilson', 'sarah.wilson@email.com', 'pass2023'),
('david_brown', 'david.brown@email.com', 'brownpass'),
('lisa_davis', 'lisa.davis@email.com', 'davispass'),
('robert_miller', 'robert.miller@email.com', 'millerpass'),
('emily_garcia', 'emily.garcia@email.com', 'garciapass'),
('james_rodriguez', 'james.rodriguez@email.com', 'jamespass'),
('maria_martinez', 'maria.martinez@email.com', 'mariapass'),
('william_anderson', 'william.anderson@email.com', 'willpass'),
('jennifer_taylor', 'jennifer.taylor@email.com', 'jenpass'),
('michael_thomas', 'michael.thomas@email.com', 'mikepass'),
('jessica_jackson', 'jessica.jackson@email.com', 'jesspass'),
('christopher_white', 'christopher.white@email.com', 'chrispass');

-- Insert Borrow Records
INSERT INTO borrow_record (user_id, book_id, borrow_date, return_date) VALUES
(1, 1, '2024-01-15', '2024-02-15'),
(2, 2, '2024-01-20', '2024-02-20'),
(3, 3, '2024-02-01', '2024-03-01'),
(4, 4, '2024-02-10', '2024-03-10'),
(5, 5, '2024-02-15', '2024-03-15'),
(1, 6, '2024-03-01', NULL), -- Still borrowed
(2, 7, '2024-03-05', '2024-04-05'),
(3, 8, '2024-03-10', NULL), -- Still borrowed
(4, 9, '2024-03-15', '2024-04-15'),
(5, 10, '2024-03-20', NULL), -- Still borrowed
(6, 11, '2024-04-01', '2024-05-01'),
(7, 12, '2024-04-05', NULL), -- Still borrowed
(8, 13, '2024-04-10', '2024-05-10'),
(9, 14, '2024-04-15', NULL), -- Still borrowed
(10, 15, '2024-04-20', '2024-05-20'),
(11, 16, '2024-05-01', NULL), -- Still borrowed
(12, 17, '2024-05-05', '2024-05-25'),
(13, 18, '2024-05-10', NULL), -- Still borrowed
(14, 19, '2024-05-15', NULL), -- Still borrowed
(15, 20, '2024-05-20', NULL); -- Still borrowed

