
interface PaymentProcessor {
    void processPayment(double amount);
}


class OldPayGateway {
    public void makeTransaction(double amt) {
        System.out.println("Transaction done using OldPay: ₹" + amt);
    }
}


class QuickPayAPI {
    public void sendPayment(double rupees) {
        System.out.println("QuickPay payment sent: ₹" + rupees);
    }
}


class OldPayAdapter implements PaymentProcessor {
    private OldPayGateway oldPay;

    public OldPayAdapter(OldPayGateway oldPay) {
        this.oldPay = oldPay;
    }

    public void processPayment(double amount) {
        oldPay.makeTransaction(amount);
    }
}


class QuickPayAdapter implements PaymentProcessor {
    private QuickPayAPI quickPay;

    public QuickPayAdapter(QuickPayAPI quickPay) {
        this.quickPay = quickPay;
    }

    public void processPayment(double amount) {
        quickPay.sendPayment(amount);
    }
}


public class Main {
    public static void main(String[] args) {
        // Using OldPay gateway through adapter
        OldPayGateway oldPay = new OldPayGateway();
        PaymentProcessor oldPayProcessor = new OldPayAdapter(oldPay);
        oldPayProcessor.processPayment(1000);

        // Using QuickPay gateway through adapter
        QuickPayAPI quickPay = new QuickPayAPI();
        PaymentProcessor quickPayProcessor = new QuickPayAdapter(quickPay);
        quickPayProcessor.processPayment(2500);
    }
}
