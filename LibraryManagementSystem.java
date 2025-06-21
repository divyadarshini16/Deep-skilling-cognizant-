import java.util.Arrays;
import java.util.Comparator;

class Book {
    int bookId;
    String title;
    String author;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    public String toString() {
        return "[" + bookId + "] " + title + " by " + author;
    }
}

public class Main {

    static Book[] books = {
        new Book(101, "The Alchemist", "Paulo Coelho"),
        new Book(102, "Atomic Habits", "James Clear"),
        new Book(103, "Clean Code", "Robert C. Martin"),
        new Book(104, "Zero to One", "Peter Thiel"),
        new Book(105, "Rich Dad Poor Dad", "Robert Kiyosaki")
    };

   
    public static Book linearSearchByTitle(String title) {
        for (Book book : books) {
            if (book.title.equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public static Book binarySearchByTitle(String title) {
   
        Arrays.sort(books, Comparator.comparing(b -> b.title.toLowerCase()));

        int low = 0, high = books.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int result = title.compareToIgnoreCase(books[mid].title);

            if (result == 0) return books[mid];
            else if (result < 0) high = mid - 1;
            else low = mid + 1;
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("Linear Search for 'Clean Code':");
        Book b1 = linearSearchByTitle("Clean Code");
        System.out.println(b1 != null ? "Found: " + b1 : "Not Found");

        System.out.println("\nBinary Search for 'Atomic Habits':");
        Book b2 = binarySearchByTitle("Atomic Habits");
        System.out.println(b2 != null ? "Found: " + b2 : " Not Found");

        System.out.println("\n Binary Search for 'Unknown Book':");
        Book b3 = binarySearchByTitle("Unknown Book");
        System.out.println(b3 != null ? " Found: " + b3 : "Not Found");
    }
}
