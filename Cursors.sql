
SET SERVEROUTPUT ON;
DECLARE

  CURSOR cur_statements IS
    SELECT customer_id, transaction_id, amount, trans_date
      FROM transactions
     WHERE TRUNC(trans_date, 'MM') = TRUNC(SYSDATE, 'MM');
  v_cust_id   transactions.customer_id%TYPE;
  v_trans_id  transactions.transaction_id%TYPE;
  v_amount    transactions.amount%TYPE;
  v_date      transactions.trans_date%TYPE;

  CURSOR cur_accounts IS
    SELECT account_id, balance
      FROM accounts;
  v_acc_id    accounts.account_id%TYPE;
  v_balance   accounts.balance%TYPE;
  v_fee       CONSTANT NUMBER := 100; -- annual fee amount

  CURSOR cur_loans IS
    SELECT loan_id, interest_rate
      FROM loans;
  v_loan_id    loans.loan_id%TYPE;
  v_int_rate   loans.interest_rate%TYPE;
  v_new_rate   NUMBER;

BEGIN
  DBMS_OUTPUT.PUT_LINE('--- Monthly Statements for ' || TO_CHAR(SYSDATE, 'MON‑YYYY') || ' ---');
  OPEN cur_statements;
  LOOP
    FETCH cur_statements INTO v_cust_id, v_trans_id, v_amount, v_date;
    EXIT WHEN cur_statements%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE('Customer '||v_cust_id||', Txn '||v_trans_id||', '||
                        TO_CHAR(v_amount,'FM9990.00')||', on '||TO_CHAR(v_date,'DD‑MON'));
  END LOOP;
  CLOSE cur_statements;

  DBMS_OUTPUT.PUT_LINE('--- Applying annual fee of '||v_fee||' to all accounts ---');
  FOR acc_rec IN cur_accounts LOOP
    UPDATE accounts
       SET balance = balance - v_fee
     WHERE account_id = acc_rec.account_id;
    DBMS_OUTPUT.PUT_LINE('Account '||acc_rec.account_id||' new balance '||
                        TO_CHAR(acc_rec.balance - v_fee,'FM9990.00'));
  END LOOP;
  COMMIT;

  DBMS_OUTPUT.PUT_LINE('--- Updating Loan Interest Rates (+0.5%) ---');
  FOR loan_rec IN cur_loans LOOP
    v_new_rate := loan_rec.interest_rate + 0.5;
    UPDATE loans
       SET interest_rate = v_new_rate
     WHERE loan_id = loan_rec.loan_id;
    DBMS_OUTPUT.PUT_LINE('Loan '||loan_rec.loan_id||' rate from '||
                        TO_CHAR(loan_rec.interest_rate,'FM90.00')||'% to '||
                        TO_CHAR(v_new_rate,'FM90.00')||'%');
  END LOOP;
  COMMIT;
END;

