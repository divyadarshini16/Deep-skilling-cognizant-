
interface Notifier {
    void send(String message);
}


class EmailNotifier implements Notifier {
    public void send(String message) {
        System.out.println("Sending Email: " + message);
    }
}


abstract class NotifierDecorator implements Notifier {
    protected Notifier wrappee;

    public NotifierDecorator(Notifier notifier) {
        this.wrappee = notifier;
    }

    public void send(String message) {
        wrappee.send(message);
    }
}


class SMSNotifierDecorator extends NotifierDecorator {
    public SMSNotifierDecorator(Notifier notifier) {
        super(notifier);
    }

    public void send(String message) {
        super.send(message); // send via wrapped notifier first
        System.out.println("Sending SMS: " + message);
    }
}


class SlackNotifierDecorator extends NotifierDecorator {
    public SlackNotifierDecorator(Notifier notifier) {
        super(notifier);
    }

    public void send(String message) {
        super.send(message); // send via wrapped notifier first
        System.out.println("Sending Slack message: " + message);
    }
}


public class Main {
    public static void main(String[] args) {
        // Base notifier
        Notifier notifier = new EmailNotifier();

        // Add SMS and Slack decorators
        Notifier smsNotifier = new SMSNotifierDecorator(notifier);
        Notifier fullNotifier = new SlackNotifierDecorator(smsNotifier);

        // Send notification using all layers
        fullNotifier.send("Server is down!");
    }
}
