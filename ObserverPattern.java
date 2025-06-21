import java.util.*;

interface Stock {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

interface Observer {
    void update(String stockName, double price);
}


class StockMarket implements Stock {
    private List<Observer> observers = new ArrayList<>();
    private String stockName;
    private double stockPrice;

    public void setStockPrice(String stockName, double price) {
        this.stockName = stockName;
        this.stockPrice = price;
        System.out.println("\nStock price updated: " + stockName + " - ₹" + price);
        notifyObservers();
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
        System.out.println("Observer registered: " + observer.getClass().getSimpleName());
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
        System.out.println("Observer removed: " + observer.getClass().getSimpleName());
    }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(stockName, stockPrice);
        }
    }
}


class MobileApp implements Observer {
    public void update(String stockName, double price) {
        System.out.println("MobileApp → " + stockName + " price changed to ₹" + price);
    }
}


class WebApp implements Observer {
    public void update(String stockName, double price) {
        System.out.println("WebApp → " + stockName + " price changed to ₹" + price);
    }
}


public class Main {
    public static void main(String[] args) {
      
        StockMarket market = new StockMarket();

        Observer mobile = new MobileApp();
        Observer web = new WebApp();

     
        market.registerObserver(mobile);
        market.registerObserver(web);

  
        market.setStockPrice("TCS", 3560.75);
        market.setStockPrice("Infosys", 1420.10);

       
        market.removeObserver(web);

   
        market.setStockPrice("HDFC Bank", 1625.40);
    }
}
