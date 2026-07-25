import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Task 03 - Book Library
 *
 * Demonstrates the use of Optional<Book> and ifPresent() to run
 * an action (printing book details) only when the book is found,
 * without needing an explicit null check.
 */
public class Task03_BookLibrary {

    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();

        // Test case 1: Existing book title
        System.out.println("---- Searching for title: 'Effective Java' ----");
        Optional<Book> bookOne = libraryService.findBookByTitle("Effective Java");
        bookOne.ifPresent(book ->
                System.out.println("Found -> Title: " + book.getTitle()
                        + " | Author: " + book.getAuthor()
                        + " | ID: " + book.getId()));
        if (!bookOne.isPresent()) {
            System.out.println("Book not found");
        }

        // Test case 2: Non-existing book title
        System.out.println("\n---- Searching for title: 'Unknown Title' ----");
        Optional<Book> bookTwo = libraryService.findBookByTitle("Unknown Title");
        bookTwo.ifPresent(book ->
                System.out.println("Found -> Title: " + book.getTitle()
                        + " | Author: " + book.getAuthor()
                        + " | ID: " + book.getId()));
        if (!bookTwo.isPresent()) {
            System.out.println("Book not found");
        }
    }
}

/**
 * Book class - represents a single book record.
 */
class Book {

    private int id;
    private String title;
    private String author;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "', author='" + author + "'}";
    }
}

/**
 * LibraryService class - contains the search logic for books.
 */
class LibraryService {

    private List<Book> bookList;

    public LibraryService() {
        bookList = new ArrayList<>();
        bookList.add(new Book(1, "Effective Java", "Joshua Bloch"));
        bookList.add(new Book(2, "Clean Code", "Robert C. Martin"));
        bookList.add(new Book(3, "Java Concurrency in Practice", "Brian Goetz"));
    }

    /**
     * Searches for a book by its title.
     * Returns Optional<Book> so the caller can use ifPresent()
     * to safely act on the result only when a match is found.
     */
    public Optional<Book> findBookByTitle(String title) {
        for (Book book : bookList) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return Optional.of(book);
            }
        }
        return Optional.empty();
    }
}
