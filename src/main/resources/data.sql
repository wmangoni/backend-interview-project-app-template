INSERT INTO DEVICE ("ID", "SYSTEM_NAME", "TYPE") VALUES
(1, 'Windows Work', 0),
(2, 'Mac', 2),
(3, 'Windows Server', 1);

INSERT INTO JOB_SERVICE ("ID", "NAME", "COST") VALUES
(1, 'Antivirus Windows', '5.0'),
(2, 'Backup', '3.0'),
(3, 'PSA', '2.0'),
(4, 'Screen Share', '1.0'),
(5, 'Antivirus Mac', '7.0');

INSERT INTO CUSTOMER VALUES
(1, 'customer A'),
(2, 'customer B'),
(3, 'customer C');

INSERT INTO CUSTOMER_DEVICE ("ID", "CUSTOMER_ID", "DEVICE_ID", "QUANTITY") VALUES
(1, 1, 1, 3),
(2, 1, 3, 2),
(3, 2, 1, 2),
(4, 2, 2, 2);

INSERT INTO CUSTOMER_JOB_SERVICE ("ID", "CUSTOMER_ID", "JOB_SERVICE_ID") VALUES
(1, 1, 1),
(2, 1, 2),
(3, 2, 3),
(4, 2, 4),
(5, 2, 1);
