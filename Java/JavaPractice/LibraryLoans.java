import java.util.*;
import java.util.stream.Collectors;

public class LibraryLoans {

    public static void main(String[] args) {
        LibraryManager manager = new LibraryManager();

        // Add Books
        manager.addBook(new Book(1, "Java Basics", "Alice"));
        manager.addBook(new Book(2, "System Design", "Bob"));
        manager.addBook(new Book(3, "Algorithms", "Charlie"));

        // Add Loans
        manager.addLoan(new Loan(1, 101, 1, -1));   // still borrowed
        manager.addLoan(new Loan(2, 101, 2, 10));   // returned
        manager.addLoan(new Loan(3, 102, 5, -1));   // still borrowed
        manager.addLoan(new Loan(2, 101, 12, -1));  // still borrowed

        // Q1 Test
        System.out.println("Currently Borrowed Count: " + manager.getCurrentlyBorrowedCount());

        // Q2 Test
        System.out.println("Borrow Count By Member: " + manager.getBorrowCountByMember());

        // Q3 Test
        System.out.println("Overdue Books: " + manager.getOverdueBooks(20, 10));
    }
}

// ------------------ MODELS ------------------

class Book {
    int bookId;
    String title;
    String author;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }
}

class Loan {
    int bookId;
    int memberId;
    int borrowDay;
    int returnDay; // -1 means not returned

    public Loan(int bookId, int memberId, int borrowDay, int returnDay) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.borrowDay = borrowDay;
        this.returnDay = returnDay;
    }

    public boolean isReturned() {
        return returnDay != -1;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getBorrowDay() {
        return borrowDay;
    }
}

// ------------------ MANAGER ------------------

class LibraryManager {
    List<Book> books = new ArrayList<>();
    List<Loan> loans = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    // Q1: FIX THIS
    public int getCurrentlyBorrowedCount() {
        int count = 0;
        for (Loan loan : loans) {
            if (!loan.isReturned())
                count++;
        }
        return count;
    }

    // Q2: IMPLEMENT
    public Map<Integer, Integer> getBorrowCountByMember() {
        if (loans == null || loans.isEmpty()) {
            return Collections.emptyMap();
        }

        return loans.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        Loan::getMemberId,
                        Collectors.summingInt(l -> 1)
                ));
    }

    // Q3: IMPLEMENT
    public List<Integer> getOverdueBooks(int currentDay, int maxLoanDays) {
        if (loans == null || currentDay < 0 || maxLoanDays < 0) {
            return Collections.emptyList();
        }

        return loans.stream()
                .filter(loan -> loan != null &&
                        currentDay - loan.getBorrowDay() > maxLoanDays &&
                        !loan.isReturned())
                .map(Loan::getBookId)
                .toList();
    }
}