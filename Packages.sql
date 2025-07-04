
CREATE OR REPLACE PACKAGE CustomerManagement AS
  PROCEDURE AddCustomer(p_id IN NUMBER, p_name IN VARCHAR2, p_age IN NUMBER);
  PROCEDURE UpdateCustomer(p_id IN NUMBER, p_name IN VARCHAR2, p_age IN NUMBER);
  FUNCTION GetCustomerBalance(p_id IN NUMBER) RETURN NUMBER;
END CustomerManagement;

CREATE OR REPLACE PACKAGE BODY CustomerManagement AS
  PROCEDURE AddCustomer(p_id IN NUMBER, p_name IN VARCHAR2, p_age IN NUMBER) IS
  BEGIN
    INSERT INTO Customers(customer_id, name, age, balance, LastModified)
    VALUES(p_id, p_name, p_age, 0, SYSDATE);
    COMMIT;
  EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN NULL;
  END AddCustomer;

  PROCEDURE UpdateCustomer(p_id IN NUMBER, p_name IN VARCHAR2, p_age IN NUMBER) IS
  BEGIN
    UPDATE Customers SET name = p_name, age = p_age, LastModified = SYSDATE
      WHERE customer_id = p_id;
    COMMIT;
  END UpdateCustomer;

  FUNCTION GetCustomerBalance(p_id IN NUMBER) RETURN NUMBER IS
    v_bal NUMBER;
  BEGIN
    SELECT balance INTO v_bal FROM Customers WHERE customer_id = p_id;
    RETURN v_bal;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN RETURN NULL;
  END GetCustomerBalance;
END CustomerManagement;


CREATE OR REPLACE PACKAGE EmployeeManagement AS
  PROCEDURE HireEmployee(p_id IN NUMBER, p_name IN VARCHAR2, p_dept IN VARCHAR2, p_sal IN NUMBER);
  PROCEDURE UpdateEmployee(p_id IN NUMBER, p_name IN VARCHAR2, p_dept IN VARCHAR2, p_sal IN NUMBER);
  FUNCTION GetAnnualSalary(p_id IN NUMBER) RETURN NUMBER;
END EmployeeManagement;

CREATE OR REPLACE PACKAGE BODY EmployeeManagement AS
  PROCEDURE HireEmployee(p_id IN NUMBER, p_name IN VARCHAR2, p_dept IN VARCHAR2, p_sal IN NUMBER) IS
  BEGIN
    INSERT INTO Employees(employee_id, name, department, salary)
    VALUES(p_id, p_name, p_dept, p_sal);
    COMMIT;
  EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN NULL;
  END HireEmployee;

  PROCEDURE UpdateEmployee(p_id IN NUMBER, p_name IN VARCHAR2, p_dept IN VARCHAR2, p_sal IN NUMBER) IS
  BEGIN
    UPDATE Employees SET name = p_name, department = p_dept, salary = p_sal
      WHERE employee_id = p_id;
    COMMIT;
  END UpdateEmployee;

  FUNCTION GetAnnualSalary(p_id IN NUMBER) RETURN NUMBER IS
    v_sal NUMBER;
  BEGIN
    SELECT salary INTO v_sal FROM Employees WHERE employee_id = p_id;
    RETURN v_sal * 12;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN RETURN NULL;
  END GetAnnualSalary;
END EmployeeManagement;

CREATE OR REPLACE PACKAGE AccountOperations AS
  PROCEDURE OpenAccount(p_acc_id IN NUMBER, p_cust_id IN NUMBER, p_type IN VARCHAR2, p_initial_balance IN NUMBER);
  PROCEDURE CloseAccount(p_acc_id IN NUMBER);
  FUNCTION GetTotalCustomerBalance(p_cust_id IN NUMBER) RETURN NUMBER;
END AccountOperations;

CREATE OR REPLACE PACKAGE BODY AccountOperations AS
  PROCEDURE OpenAccount(p_acc_id IN NUMBER, p_cust_id IN NUMBER, p_type IN VARCHAR2, p_initial_balance IN NUMBER) IS
  BEGIN
    INSERT INTO Accounts(account_id, customer_id, account_type, balance)
    VALUES(p_acc_id, p_cust_id, p_type, p_initial_balance);
    COMMIT;
  END OpenAccount;

  PROCEDURE CloseAccount(p_acc_id IN NUMBER) IS
  BEGIN
    DELETE FROM Accounts WHERE account_id = p_acc_id;
    COMMIT;
  END CloseAccount;

  FUNCTION GetTotalCustomerBalance(p_cust_id IN NUMBER) RETURN NUMBER IS
    v_total NUMBER;
  BEGIN
    SELECT SUM(balance) INTO v_total
      FROM Accounts
     WHERE customer_id = p_cust_id;
    RETURN NVL(v_total,0);
  END GetTotalCustomerBalance;
END AccountOperations;


SHOW ERRORS;
