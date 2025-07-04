

CREATE OR REPLACE PACKAGE bank_operations AS
  PROCEDURE ProcessMonthlyInterest;
  PROCEDURE UpdateEmployeeBonus(p_dept_id IN VARCHAR2, p_bonus_pct IN NUMBER);
  PROCEDURE TransferFunds(p_from_acct IN NUMBER, p_to_acct IN NUMBER, p_amount IN NUMBER);
END bank_operations;

SHOW ERRORS PACKAGE bank_operations;

CREATE OR REPLACE PACKAGE BODY bank_operations AS

  PROCEDURE ProcessMonthlyInterest IS
  BEGIN
    UPDATE accounts
       SET balance = balance * 1.01
     WHERE account_type = 'SAVINGS';
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Monthly interest applied to savings accounts.');
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('Error in ProcessMonthlyInterest: ' || SQLERRM);
  END ProcessMonthlyInterest;

  PROCEDURE UpdateEmployeeBonus(p_dept_id IN VARCHAR2, p_bonus_pct IN NUMBER) IS
    v_rows_updated NUMBER;
  BEGIN
    UPDATE employees
       SET salary = salary * (1 + p_bonus_pct/100)
     WHERE department = p_dept_id;
    v_rows_updated := SQL%ROWCOUNT;

    IF v_rows_updated = 0 THEN
      DBMS_OUTPUT.PUT_LINE('No employees updated in department "' || p_dept_id || '".');
    ELSE
      DBMS_OUTPUT.PUT_LINE('Bonuses applied to ' || v_rows_updated || ' employees in department "' || p_dept_id || '".');
    END IF;

    COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('Error in UpdateEmployeeBonus: ' || SQLERRM);
  END UpdateEmployeeBonus;

  PROCEDURE TransferFunds(p_from_acct IN NUMBER, p_to_acct IN NUMBER, p_amount IN NUMBER) IS
    v_from_bal NUMBER;
  BEGIN
    SELECT balance INTO v_from_bal FROM accounts WHERE account_id = p_from_acct;

    IF v_from_bal < p_amount THEN
      RAISE_APPLICATION_ERROR(-20002, 'Insufficient funds in source account ' || p_from_acct);
    END IF;

    UPDATE accounts SET balance = balance - p_amount WHERE account_id = p_from_acct;
    UPDATE accounts SET balance = balance + p_amount WHERE account_id = p_to_acct;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Transferred ' || p_amount || ' from acct ' || p_from_acct || ' to acct ' || p_to_acct);
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('Account not found: ' || p_from_acct || ' or ' || p_to_acct);
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('Error in TransferFunds: ' || SQLERRM);
  END TransferFunds;

END bank_operations;

SHOW ERRORS PACKAGE BODY bank_operations;
