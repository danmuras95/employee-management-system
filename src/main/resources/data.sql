-- Departments table
CREATE TABLE IF NOT EXISTS department (
    id SERIAL PRIMARY KEY,
    location VARCHAR(255),
    name VARCHAR(255)
);

-- Pre-populate Departments
INSERT INTO department (id, location, name) VALUES
(1, 'Floor 7', 'Human Resources'),
(2, 'Floor 6', 'Finance'),
(3, 'Floor 5', 'Sales'),
(4, 'Floor 4', 'Marketing'),
(5, 'Floor 3', 'Operations'),
(6, 'Floor 2', 'IT / Technology'),
(7, 'Floor 1', 'Customer Support');

-- Employees table
CREATE TABLE IF NOT EXISTS employee (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    email VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    role VARCHAR(50),
    salary DECIMAL(10,2),
    department_id INT,
    manager_id INT
);

-- Pre-populate Employees
INSERT INTO employee (id, created_at, email, first_name, last_name, role, salary, department_id, manager_id) VALUES
(6,'2026-03-20 14:49:01.02072','anne.shirley@email.com','Anne','Shirley','TEAM_LEAD',60000,2,3),
(8,'2026-03-20 15:03:05.508278','beatrix.potter@email.com','Beatrix','Potter','MANAGER',100000,3,NULL),
(3,'2026-03-20 13:22:50.069061','vera.claythorne@email.com','Vera','Claythorne','MANAGER',55000,1,NULL),
(4,'2026-03-20 13:30:56.476682','philip.lombard@email.com','Philip','Lombard','MANAGER',65000,1,NULL),
(7,'2026-03-20 14:49:56.212589','gilbert.blythe@email.com','Gilbert','Blythe','TEAM_LEAD',60000,2,4),
(41,'2026-03-29 19:01:00.766266','elizabeth.bennet@email.com','Elizabeth','Bennet','MANAGER',75000,7,NULL),
(42,'2026-03-29 19:01:25.732474','charlotte.lucas@email.com','Charlotte','Lucas','MANAGER',70000,6,NULL),
(43,'2026-03-29 19:06:23.505825','joe.goldberg@email.com','Joe','Goldberg','MANAGER',80000,2,NULL),
(44,'2026-03-29 19:07:58.555392','connel.waldron@email.com','Connel','Waldron','MANAGER',85000,5,NULL),
(45,'2026-03-29 19:08:36.019771','dianna.barry@email.com','Dianna','Barry','MANAGER',90000,4,NULL),
(46,'2026-03-29 19:10:36.434288','veronica.whittaker@email.com','Veronica','Whittaker','MANAGER',95000,3,NULL),
(47,'2026-04-02 19:10:36.434288','marianne.sheridan@email.com','Marianne','Sheridan','MANAGER',85000,5,NULL);

-- Leave Requests table
CREATE TABLE IF NOT EXISTS leave_request (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    end_date DATE,
    leave_status VARCHAR(50),
    leave_type VARCHAR(50),
    start_date DATE,
    employee_id INT
);

-- Pre-populate Leave Requests
INSERT INTO leave_request (id, created_at, end_date, leave_status, leave_type, start_date, employee_id) VALUES
(1,'2026-03-20 15:23:42.669233','2026-04-28','CANCELLED','VACATION','2026-04-20',3),
(2,'2026-03-20 15:30:48.110438','2026-04-28','PENDING','BUSINESS_TRIP','2026-04-20',4),
(3,'2026-03-20 15:33:36.929619','2026-11-20','PENDING','MATERNITY','2026-05-20',6),
(4,'2026-03-20 15:33:55.300035','2026-08-20','PENDING','PATERNITY','2026-05-20',7),
(5,'2026-03-20 15:34:59.933062','2026-04-28','PENDING','VACATION','2026-04-20',8),
(6,'2026-03-20 15:35:10.569215','2026-04-28','CANCELLED','VACATION','2026-04-20',8),
(7,'2026-03-20 15:35:14.261707','2026-04-28','CANCELLED','VACATION','2026-04-20',8),
(8,'2026-04-02 15:23:42.669233','2026-04-10','APPROVED','SICK','2026-04-02',41),
(9,'2026-04-02 15:23:42.669233','2026-04-24','APPROVED','UNPAID','2026-04-13',42),
(10,'2026-04-01 15:23:42.669233','2026-05-04','PENDING','BUSINESS_TRIP','2026-05-18',43),
(11,'2026-03-31 12:23:42.669233','2026-04-10','REJECTED','SICK','2026-04-06',43),
(12,'2026-04-02 12:43:42.669233','2026-06-12','APPROVED','VACATION','2026-06-01',47),
(13,'2026-04-02 12:40:42.669233','2026-06-12','APPROVED','VACATION','2026-06-01',44);

-- Fix sequences
SELECT setval(pg_get_serial_sequence('leave_request', 'id'), (SELECT MAX(id) FROM leave_request));
SELECT setval(pg_get_serial_sequence('employee', 'id'), (SELECT MAX(id) FROM employee));
SELECT setval(pg_get_serial_sequence('department', 'id'), (SELECT MAX(id) FROM department));