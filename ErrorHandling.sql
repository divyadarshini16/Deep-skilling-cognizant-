
CREATE OR REPLACE PACKAGE bank_ops AS
  PROCEDURE SafeTransferFunds(
    p_from_acct IN NUMBER,
    p_to_acct   IN NUMBER,
    p_amount    IN NUMBER
  );

  PROCEDURE UpdateSalary(
    p_emp_id       IN NUMBER,
    p_pct_increase IN NUMBER
  );

  PROCEDURE AddNewCustomer(
    p_cust_id IN NUMBER,
    p_name    IN VARCHAR2,
    p_age     IN NUMBER,
    p_balance IN NUMBER
  );
END bank_ops;

SHOW ERRORS PACKAGE bank_ops;

CREATE OR REPLACE PACKAGE BODY bank_ops AS

 
  PROCEDURE SafeTransferFunds(
    p_from_acct IN NUMBER,
    p_to_acct   IN NUMBER,
    p_amount    IN NUMBER
  ) AS
    insufficient_funds EXCEPTION;
    PRAGMA EXCEPTION_INIT(insufficient_funds, -20001);
    v_balance_from NUMBER;
  BEGIN
    SELECT balance INTO v_balance_from
      FROM accounts
     WHERE account_id = p_from_acct;

    IF v_balance_from < p_amount THEN
      RAISE insufficient_funds;
    END IF;

    UPDATE accounts
       SET balance = balance - p_amount
     WHERE account_id = p_from_acct;

    UPDATE accounts
       SET balance = balance + p_amount
     WHERE account_id = p_to_acct;

    COMMIT;
  EXCEPTION
    WHEN insufficient_funds THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('Transfer failed: insufficient funds in account ' || p_from_acct);
    WHEN NO_DATA_FOUND THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('Transfer failed: account not found.');
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('Transfer failed due to: ' || SQLERRM);
  END SafeTransferFunds;


  PROCEDURE UpdateSalary(
    p_emp_id       IN NUMBER,
    p_pct_increase IN NUMBER
  ) AS
    v_rows_updated NUMBER;
  BEGIN
    UPDATE employees
       SET salary = salary * (1 + p_pct_increase / 100)
     WHERE employee_id = p_emp_id;

    v_rows_updated := SQL%ROWCOUNT;
    IF v_rows_updated = 0 THEN
      RAISE NO_DATA_FOUND;
    END IF;

    COMMIT;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('UpdateSalary failed: employee ID ' || p_emp_id || ' not found.');
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('UpdateSalary failed: ' || SQLERRM);
  END UpdateSalary;


  PROCEDURE AddNewCustomer(
    p_cust_id IN NUMBER,
    p_name    IN VARCHAR2,
    p_age     IN NUMBER,
    p_balance IN NUMBER
  ) AS
  BEGIN
    INSERT INTO customers (customer_id, name, age, balance)
    VALUES (p_cust_id, p_name, p_age, p_balance);

    COMMIT;
  EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('AddNewCustomer failed: customer ID ' || p_cust_id || ' already exists.');
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('AddNewCustomer failed: ' || SQLERRM);
  END AddNewCustomer;

END bank_ops;

