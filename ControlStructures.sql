SET SERVEROUTPUT ON;
DECLARE
  CURSOR cust_cur IS
    SELECT customer_id, age, loan_interest, balance
      FROM customers
     FOR UPDATE OF loan_interest, balance;
  
  CURSOR loan_cur IS
    SELECT l.loan_id,
           l.customer_id,
           c.name   AS cust_name,
           l.due_date
      FROM loans l
      JOIN customers c USING (customer_id)
     WHERE l.due_date BETWEEN TRUNC(SYSDATE)
                         AND TRUNC(SYSDATE) + 30;

BEGIN
  FOR rec IN cust_cur LOOP
    IF rec.age > 60 THEN
      UPDATE customers
         SET loan_interest = rec.loan_interest - 0.01
       WHERE CURRENT OF cust_cur;
    END IF;

    IF rec.balance > 10000 THEN
      UPDATE customers
         SET IsVIP = 'Y'
       WHERE CURRENT OF cust_cur;
    END IF;
  END LOOP;

  COMMIT;

  FOR lrec IN loan_cur LOOP
    DBMS_OUTPUT.PUT_LINE(
      'Reminder: Customer "' || lrec.cust_name ||
      '" (ID ' || lrec.customer_id || ') has loan ' ||
      lrec.loan_id || ' due on ' ||
      TO_CHAR(lrec.due_date, 'DD-MON-YYYY')
    );
  END LOOP;
END;

