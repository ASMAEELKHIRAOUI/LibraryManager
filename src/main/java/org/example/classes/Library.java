package org.example.classes;

import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Random;

import static org.example.classes.Borrowing.getAllBorrowings;

public class Library {
    private static Connection connection;

    public Library() {
    }

    public static void main(String[] args) {
        connection = DBConnection.createDBConnection();
        if (connection != null) {
            updateLostStatus();
            menu();
        } else {
            System.err.println("Failed to establish a database connection. Exiting.");
        }

    }

    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        boolean next = false;

        while (!next) {
            System.out.println("********Menu********");
            System.out.println("1. List Books");
            System.out.println("2. Add a book");
            System.out.println("3. Update a book");
            System.out.println("4. Delete a book");
            System.out.println("5. Search for a book");
            System.out.println("6. Borrow a book");
            System.out.println("7. Return a book");
            System.out.println("8. Statistics");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    listBooks((String) null);
                    break;
                case 2:
                    addBook();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    deleteBook();
                    break;
                case 5:
                    search();
                    break;
                case 6:
                    borrowBook();
                    break;
                case 7:
                    returnBook();
                    break;
                case 8:
                    statistics();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    public static void listBooks(String search) {
        new ArrayList();
        List books;
        if (search == null) {
            books = Book.getAllBooks((String) null);
        } else {
            books = Book.getAllBooks(search);
        }

        Iterator booksList = books.iterator();

        while (booksList.hasNext()) {
            Book book = (Book) booksList.next();
            System.out.println("ID: " + book.getId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor().getName());
            System.out.println("ISBN: " + book.getIsbn());
            System.out.println("Available: " + book.getAvailable());
            System.out.println("Borrowed: " + book.getBorrowed());
            System.out.println("Lost: " + book.getLost());
            System.out.println();
        }

        if (books.isEmpty()) {
            System.out.println("No books found.");
        }

    }

    public static void search() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the book's title or its author");
        String search = scanner.nextLine();
        listBooks(search);
    }

    public static void addBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter book's info:\n");
        System.out.print("title: ");
        String title = scanner.nextLine();
        System.out.print("isbn: ");
        String isbn = scanner.nextLine();
        List<Author> authors = Author.read();
        System.out.println("Authors in the database:");
        Iterator authorsList = authors.iterator();

        Author author;
        while (authorsList.hasNext()) {
            author = (Author) authorsList.next();
            System.out.println(author.getId() + ". " + author.getName());
        }

        System.out.print("Select author (type 0 if the author's not found): ");
        int authorID = scanner.nextInt();
        author = null;
        if (authorID == 0) {
            System.out.print("Enter author's name: ");
            scanner.nextLine();
            String authorName = scanner.nextLine();
            author = Author.create(authorName);
        } else {
            Iterator authorslist = authors.iterator();

            while (authorslist.hasNext()) {
                Author a = (Author) authorslist.next();
                if (a.getId() == authorID) {
                    author = a;
                    break;
                }
            }
        }

        System.out.print("Copies available: ");
        int available = scanner.nextInt();

        Book.create(title, author, isbn, available);
    }

    public static void updateBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("**********Books available**********");
        listBooks((String) null);
        System.out.println("************************************");
        System.out.print("Enter the ID of the book to update: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        Book.update(bookId);
    }

    public static void deleteBook() {
        System.out.println("**********Books available**********");
        listBooks((String) null);
        Scanner scanner = new Scanner(System.in);
        System.out.println("************************************");
        System.out.print("Enter the ID of the book to delete: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        Book.delete(bookId);
    }

    public static void borrowBook() {
        Scanner scanner = new Scanner(System.in);
        List<Book> books = Book.getAllBooks((String) null);
        System.out.print("Enter the ISBN of the book to borrow: ");
        String bookISBN = scanner.nextLine();
        Book book = null;
        Iterator booksList = books.iterator();

        while (booksList.hasNext()) {
            Book b = (Book) booksList.next();
            //System.out.println(b.getIsbn().trim());
            if (Objects.equals(b.getIsbn().trim(), bookISBN.trim())) {
                book = b;
                break;
            }
        }

        if (book == null) {
            System.out.println("Book with ISBN " + bookISBN + " not found.");
        } else {
            List<Member> members = Member.read();
            System.out.print("Type the client's membership (type 0 to add one): ");
            String membership = scanner.next();
            Member member = null;
            scanner.nextLine();
            if (membership.equals("0")) {
                System.out.print("Enter member's name: ");
                String memberName = scanner.nextLine();
                Random random = new Random();
                int numDigits = 14;
                StringBuilder stringBuilder = new StringBuilder(numDigits);
                for (int i = 0; i < numDigits; i++) {
                    int digit = random.nextInt(10);
                    stringBuilder.append(digit);
                }
                String generatedMembership = stringBuilder.toString();
                member = Member.create(memberName, generatedMembership);
            } else {
                Iterator membersList = members.iterator();

                while (membersList.hasNext()) {
                    Member m = (Member) membersList.next();
                    if (Objects.equals(m.getMembership().trim(), membership.trim())) {
                        member = m;
                        break;
                    }
                }
            }
            if (member == null) {
                System.out.println("Member with membership " + membership + " not found.");
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                String borrowingDate = dateFormat.format(calendar.getTime());

                boolean validReturnDate = false;
                Date returnDate = null;

                while (!validReturnDate) {
                    System.out.print("Return date (YYYY-MM-DD): ");
                    String returnDateStr = scanner.next();
                    returnDate = Date.valueOf(returnDateStr);
                    LocalDate today = LocalDate.now();
                    LocalDate selectedReturnDate = returnDate.toLocalDate();

                    if (selectedReturnDate.isBefore(today)) {
                        System.out.println("Invalid return date. Please enter a date in the future.");
                    } else {
                        validReturnDate = true;
                    }
                }

                Book.borrow(returnDate, book, member, borrowingDate);

            }
        }
    }

    public static void returnBook() {
        Scanner scanner = new Scanner(System.in);
        List<Book> books = Book.AllBooks();
        System.out.print("Enter the ISBN of the book to return: ");
        String bookISBN = scanner.nextLine();
        Book book = null;
        Iterator booksList = books.iterator();

        while (booksList.hasNext()) {
            Book b = (Book) booksList.next();
            if (Objects.equals(b.getIsbn().trim(), bookISBN.trim())) {
                book = b;
                break;
            }
        }

        if (book == null) {
            System.out.println("Book with ISBN " + bookISBN + " not found.");
        } else {
            List<Member> members = Member.read();
            System.out.print("Type the client's membership: ");
            String membership = scanner.next();
            Member member = null;
            scanner.nextLine();
            Iterator memberList = members.iterator();

            while (memberList.hasNext()) {
                Member m = (Member) memberList.next();
                if (Objects.equals(m.getMembership().trim(), membership.trim())) {
                    member = m;
                    break;
                }
            }

            if (member == null) {
                System.out.println("Member with membership " + membership + " not found.");
            } else {
                Book.returning(member,book);
            }
        }
    }

    public static void statistics() {
        Scanner scanner = new Scanner(System.in);
        boolean next = false;

        while (!next) {
            System.out.println("********Statistics********");
            System.out.println("1. Available Books");
            System.out.println("2. Borrowed Books");
            System.out.println("3. Lost Books");
            System.out.println("4. Total of Books");
            System.out.println("5. Total of available books");
            System.out.println("6. Total of borrowed books");
            System.out.println("7. Total of lost books");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int statisticChoice = scanner.nextInt();
            scanner.nextLine();
            switch (statisticChoice) {
                case 1:
                    availableBooks();
                    break;
                case 2:
                    borrowedBooks();
                    break;
                case 3:
                    lostBooks();
                    break;
                case 4:
                    //totalBooks();
                    break;
                case 5:
                    System.out.println("total of available books: " + Book.totalBooks("available"));
                    break;
                case 6:
                    System.out.println("total of borrowed books: " + Book.totalBooks("borrowed"));
                    break;
                case 7:
                    System.out.println("total of lost books: " + Book.totalBooks("lost"));
                    break;
                case 0:
                    menu();
                    break;
                case 8:
                    statistics();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");

            }
        }
    }

    public static void availableBooks() {

        new ArrayList();
        List books;
        books = Book.getAllBooks((String) null);

        Iterator booksList = books.iterator();

        while (booksList.hasNext()) {
            Book book = (Book) booksList.next();
            System.out.println(book.getTitle() + " by " + book.getAuthor().getName() + " : " + book.getAvailable());
            System.out.println();
        }

        if (books.isEmpty()) {
            System.out.println("No books found.");
        }
    }

    public static void borrowedBooks() {

        new ArrayList();
        List books;
        books = Book.getBorrowedBooks();

        Iterator booksList = books.iterator();

        while (booksList.hasNext()) {
            Book book = (Book) booksList.next();
            System.out.println(book.getTitle() + " by " + book.getAuthor().getName() + " : " + book.getBorrowed());
            System.out.println();
        }

        if (books.isEmpty()) {
            System.out.println("No books found.");
        }
    }

    public static void lostBooks() {

        new ArrayList();
        List books;
        books = Book.getLostBooks();

        Iterator booksList = books.iterator();

        while (booksList.hasNext()) {
            Book book = (Book) booksList.next();
            System.out.println(book.getTitle() + " by " + book.getAuthor().getName() + " : " + book.getLost());
            System.out.println();
        }

        if (books.isEmpty()) {
            System.out.println("No books found.");
        }
    }

    public static void updateLostStatus() {
        List<Borrowing> borrowings = getAllBorrowings();

        if (borrowings != null) {
            for (Borrowing borrowing : borrowings) {
                LocalDate today = LocalDate.now();
                LocalDate returnLocalDate = LocalDate.parse(borrowing.getReturnDate());

                if (!borrowing.getLost() && returnLocalDate.isBefore(today)) {
                    borrowing.setLost(true);
                    Borrowing.updateBorrowing(borrowing);
                }
            }
        }
    }

}