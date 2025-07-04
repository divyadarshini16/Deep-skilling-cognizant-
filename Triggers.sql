
CREATE OR REPLACE TRIGGER UpdateCustomerLastModified
  BEFORE UPDATE ON Customers
  FOR EACH ROW
BEGIN
  :NEW.LastModified := SYSDATE;
END UpdateCustomerLastModified;

SHOW ERRORS TRIGGER UpdateCustomerLastModified;

CREATE OR REPLACE TRIGGER LogTransaction
  AFTER INSERT ON Transactions
  FOR EACH ROW
BEGIN
  INSERT INTO AuditLog (
    AuditID,
    TransactionID,
    AccountID,
    Amount,
    TransDate,
    ChangeUser,
    ChangeTime
  ) VALUES (
    AuditLogSeq.NEXTVAL,
    :NEW.TransactionID,
    :NEW.AccountID,
    :NEW.Amount,
    :NEW.TransDate,
    USER,
    SYSDATE
  );
END LogTransaction;

SHOW ERRORS TRIGGER LogTransaction;

CREATE OR REPLACE TRIGGER CheckTransactionRules
  BEFORE INSERT ON Transactions
  FOR EACH ROW
DECLARE
  v_balance NUMBER;
BEGIN
  IF :NEW.Amount = 0 THEN
    RAISE_APPLICATION_ERROR(-20001, 'Transaction amount cannot be zero.');
  END IF;

  SELECT balance
    INTO v_balance
    FROM Accounts
   WHERE account_id = :NEW.AccountID;

  IF :NEW.Amount < 0 AND v_balance < ABS(:NEW.Amount) THEN
    RAISE_APPLICATION_ERROR(-20002,
      'Insufficient funds for withdrawal: current balance is ' || v_balance);
  END IF;

  IF :NEW.Amount > 0 THEN
    -- Optionally check max deposit amount or other rules here
    NULL;
  END IF;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RAISE_APPLICATION_ERROR(-20003,
      'Account ' || :NEW.AccountID || ' does not exist.');
END CheckTransactionRules;

SHOW ERRORS TRIGGER CheckTransactionRules;
