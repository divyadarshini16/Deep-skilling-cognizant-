

CREATE OR REPLACE PACKAGE bank_utils AS
  FUNCTION CalculateAge(p_dob IN DATE) RETURN PLS_INTEGER;
  FUNCTION CalculateMonthlyInstallment(
    p_principal   IN NUMBER,
    p_annual_rate IN NUMBER,
    p_years       IN NUMBER
  ) RETURN NUMBER;
  FUNCTION HasSufficientBalance(
    p_account_id IN NUMBER,
    p_amount     IN NUMBER
  ) RETURN BOOLEAN;
END bank_utils;

SHOW ERRORS PACKAGE bank_utils;

CREATE OR REPLACE PACKAGE BODY bank_utils AS

  FUNCTION CalculateAge(p_dob IN DATE) RETURN PLS_INTEGER IS
  BEGIN
    RETURN FLOOR(MONTHS_BETWEEN(SYSDATE, p_dob) / 12);
  END CalculateAge;
  
  FUNCTION CalculateMonthlyInstallment(
    p_principal   IN NUMBER,
    p_annual_rate IN NUMBER,
    p_years       IN NUMBER
  ) RETURN NUMBER IS
    v_monthly_rate NUMBER := p_annual_rate / 100 / 12;
    v_months       NUMBER := p_years * 12;
  BEGIN
    RETURN (p_principal * v_monthly_rate) /
           (1 - POWER(1 + v_monthly_rate, -v_months));
  END CalculateMonthlyInstallment;


  FUNCTION HasSufficientBalance(
    p_account_id IN NUMBER,
    p_amount     IN NUMBER
  ) RETURN BOOLEAN IS
    v_bal NUMBER;
  BEGIN
    SELECT balance INTO v_bal 
      FROM accounts 
     WHERE account_id = p_account_id;

    RETURN v_bal >= p_amount;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      RETURN FALSE;
  END HasSufficientBalance;

END bank_utils;

SHOW ERRORS PACKAGE BODY bank_utils;
