import java.util.*;
import java.util.stream.Collectors;

public class TransactionSystem {
    public static void main(String[] args) {

        TransactionManager manager = new TransactionManager();

        manager.addTransaction(new Transaction(101, 100, "DEPOSIT", 1));
        manager.addTransaction(new Transaction(101, 50, "WITHDRAW", 5));
        manager.addTransaction(new Transaction(101, 70, "DEPOSIT", 8)); // suspicious

        manager.addTransaction(new Transaction(102, 200, "DEPOSIT", 2));
        manager.addTransaction(new Transaction(102, 100, "WITHDRAW", 10));
        manager.addTransaction(new Transaction(102, 50, "DEPOSIT", 20)); // not suspicious

        manager.addTransaction(new Transaction(103, 300, "DEPOSIT", 1));

        // Q1
        System.out.println("Balance 101: " + manager.getBalance(101));
        // Expected: 120

        // Q2
        System.out.println("Balance Per Account: " + manager.getBalancePerAccount());
        // Expected: {101=120, 102=150, 103=300}

        // Q3
        System.out.println("Suspicious Accounts: " + manager.getSuspiciousAccounts());
        // Expected: [101]

        // Edge cases
        TransactionManager empty = new TransactionManager();
        System.out.println("Empty Balances: " + empty.getBalancePerAccount()); // {}
        System.out.println("Empty Suspicious: " + empty.getSuspiciousAccounts()); // []
    }
}

// ------------------ MODEL ------------------

class Transaction {
    int accountId;
    int amount;
    String type; // "DEPOSIT" or "WITHDRAW"
    int timestamp;

    public Transaction(int accountId, int amount, String type, int timestamp) {
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
    }

    public int getAccountId() { return accountId; }
    public int getAmount() { return amount; }
    public String getType() { return type; }
    public int getTimestamp() { return timestamp; }
}

// ------------------ MANAGER ------------------

class TransactionManager {
    List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    // Q1: FIX THIS
    public int getBalance(int accountId) {
        int balance = 0;
        for (Transaction t : transactions) {
            if (t.getAccountId() == accountId) {
                if ("DEPOSIT".equalsIgnoreCase(t.getType())) {
                    balance += t.getAmount();
                } else if ("WITHDRAW".equalsIgnoreCase(t.getType())) {
                    balance -= t.getAmount();
                }
            }
        }
        return balance;
    }

    // Q2: IMPLEMENT
    public Map<Integer, Integer> getBalancePerAccount() {
        if (transactions.isEmpty()){
            return Collections.emptyMap();
        }

        return transactions.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        Transaction::getAccountId,
                        t -> getBalance(t.getAccountId()),
                        (a1, a2) -> a1
                ));
    }

    // Q3: IMPLEMENT
    public List<Integer> getSuspiciousAccounts() {
        if (transactions.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Integer, Transaction> map = new HashMap<>();
        Set<Integer> result = new HashSet<>();

        transactions.sort(Comparator.comparing(Transaction::getTimestamp));

        for (Transaction t : transactions) {
            if (t == null) {continue;}

            Transaction prev = map.get(t.getAccountId());

            if (prev != null &&
                    "WITHDRAW".equalsIgnoreCase(prev.getType()) &&
                    "DEPOSIT".equalsIgnoreCase(t.getType()) &&
                    t.getTimestamp() - map.get(t.getAccountId()).getTimestamp() <= 5) {
                result.add(t.getAccountId());
            }
            map.put(t.getAccountId(), t);
        }

        return result.stream().toList();
    }
}