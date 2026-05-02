import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class StockAnalysis {
    public static void main(String[] args) {

        // --- Q1 test ---
        StockCollection aapl = new StockCollection("AAPL", 10);
        aapl.record(LocalDate.of(2024, 1, 1), 182.00);
        aapl.record(LocalDate.of(2024, 1, 2), 184.00);
        aapl.record(LocalDate.of(2024, 1, 3), 185.00);

        StockCollection msft = new StockCollection("MSFT", 5);
        msft.record(LocalDate.of(2024, 1, 1), 374.00);
        msft.record(LocalDate.of(2024, 1, 2), 376.00);
        msft.record(LocalDate.of(2024, 1, 3), 370.00);

        System.out.println("AAPL latest: " + aapl.latestPrice());   // Expected: 185.00
        System.out.println("MSFT latest: " + msft.latestPrice());   // Expected: 370.00

        // --- Q2 test ---
        System.out.println("AAPL biggest change: " + Arrays.toString(aapl.getBiggestChange()));
        // Expected: [2.0, [2024-01-01, 2024-01-02]]
        System.out.println("MSFT biggest change: " + Arrays.toString(msft.getBiggestChange()));
        // Expected: [-6.0, [2024-01-02, 2024-01-03]]

        // --- Q3 test ---
        List<StockCollection> portfolio = List.of(aapl, msft);
        System.out.println("Portfolio total: " + StockCollection.getTotal(portfolio));
        // Expected: (10 * 185.00) + (5 * 370.00) = 3700.0
    }
}

// ------------------ MODEL ------------------

class StockCollection {
    public String symbol;
    public int shares;
    public TreeMap<LocalDate, Double> prices = new TreeMap<>();

    public StockCollection(String symbol, int shares) {
        this.symbol = symbol;
        this.shares = shares;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getShares() {
        return shares;
    }

    public TreeMap<LocalDate, Double> getPrices() {
        return prices;
    }

    public void record(LocalDate d, double price) {
        prices.put(d, price);
    }

    // Q1: BUG — latestPrice() should return the price for the most recent date,
    //     but it always returns the price for the EARLIEST date instead.
    //     Fix it.
    public double latestPrice() {
        if (prices.isEmpty()) throw new IllegalStateException("no price data");
        return prices.lastEntry().getValue();  // BUG
    }

    // Q2: IMPLEMENT
    // Return Object[] { delta, List<LocalDate> } where delta is the signed price
    // change of the largest absolute move between consecutive dates.
    // Return null if fewer than 2 price records exist.
    public Object[] getBiggestChange() {
        if (prices.size() < 2) {
            return null;
        }

        List<List<Object>> list = new ArrayList<>();
        for (Map.Entry<LocalDate, Double> entry : prices.entrySet()) {
            list.add(Arrays.asList(entry.getKey(), entry.getValue()));
        }

        Object[] result = new Object[2];
        double max = -1.0;

        for (int i = 0; i < list.size()-1; i++){
            double temp = (double) list.get(i+1).get(1) - (double) list.get(i).get(1);
            if (Math.abs(temp) > max) {
                max = Math.abs(temp);
                result[0] = max;
                result[1] = Arrays.asList(list.get(i).get(0), list.get(i+1).get(0));
            }
        }
        return result;
    }

    // Q3: IMPLEMENT
    // Return sum of (sc.shares * sc.latestPrice()) for each collection.
    // Skip collections with no price data.
    public static double getTotal(List<StockCollection> collections) {
        double sum = 0.0;
        for (StockCollection s : collections) {
            if (s.prices.isEmpty()) continue;
            sum += s.getShares() * s.latestPrice();
        }
        return sum;
    }
}