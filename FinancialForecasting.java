public class Main {

    public static double forecastRecursive(double presentValue, double rate, int years) {
        if (years == 0) return presentValue;
        return forecastRecursive(presentValue, rate, years - 1) * (1 + rate);
    }

    public static double forecastEfficient(double presentValue, double rate, int years) {
        return presentValue * Math.pow(1 + rate, years);
    }

    public static void main(String[] args) {
        double presentValue = 10000; 
        double growthRate = 0.10;   
        int years = 5;

        System.out.println("Forecasting using recursion:");
        double result1 = forecastRecursive(presentValue, growthRate, years);
        System.out.printf("Future Value (recursive) after %d years: ₹%.2f\n", years, result1);

        System.out.println("\n Forecasting using optimized method:");
        double result2 = forecastEfficient(presentValue, growthRate, years);
        System.out.printf("Future Value (efficient) after %d years: ₹%.2f\n", years, result2);
    }
}
