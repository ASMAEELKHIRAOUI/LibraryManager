package org.example.classes;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Book {
    private static Connection connection = DBConnection.createDBConnection();
    private int id;
    private String title;
    private Author author;
    private String isbn;
    private int available;
    private int borrowed;
    private int lost;
    private static List<Book> books = new ArrayList();

    public Book() {
    }

    public Book(String title, Author author, String isbn, int available, int borrowed, int lost) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = available;
        this.borrowed = borrowed;
        this.lost = lost;
    }

    public static List<Book> getAllBooks(String search) {
        List<Book> books = new ArrayList();

        try {
            String query = "SELECT b.*, a.id AS author_id, a.name AS author_name\nFROM books AS b\nJOIN authors AS a ON b.author_id = a.id WHERE b.available > 0";
            if (search != null) {
                query = query + " AND (b.title LIKE ? OR a.name LIKE ?)";
            }

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            if (search != null) {
                String searchParam = "%" + search + "%";
                preparedStatement.setString(1, searchParam);
                preparedStatement.setString(2, searchParam);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int authorId = resultSet.getInt("author_id");
                String authorName = resultSet.getString("author_name");
                String isbn = resultSet.getString("isbn");
                int available = resultSet.getInt("available");
                int borrowed = resultSet.getInt("borrowed");
                int lost = resultSet.getInt("lost");
                Author author = new Author(authorId, authorName);
                Book book = new Book(title, author, isbn, available, borrowed, lost);
                book.setId(id);
                books.add(book);
            }

            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int totalBooks(String status) {
        try {
            String columnToSum;

            switch (status) {
                case "available":
                    columnToSum = "available";
                    break;
                case "borrowed":
                    columnToSum = "borrowed";
                    break;
                case "lost":
                    columnToSum = "lost";
                    break;
                default:
                    throw new IllegalArgumentException("Invalid status: " + status);
            }

            String query = "SELECT SUM(" + columnToSum + ") AS total FROM books;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int total = resultSet.getInt("total");
                return total;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Book> AllBooks() {
            try {
                String query = "SELECT b.*, a.id AS author_id, a.name AS author_name\nFROM books AS b\nJOIN authors AS a ON b.author_id = a.id;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    int authorId = resultSet.getInt("author_id");
                    String authorName = resultSet.getString("author_name");
                    String isbn = resultSet.getString("isbn");
                    int available = resultSet.getInt("available");
                    int borrowed = resultSet.getInt("borrowed");
                    int lost = resultSet.getInt("lost");
                    Author author = new Author(authorId, authorName); // Create Author with ID and Name
                    Book book = new Book(title, author, isbn, available, borrowed, lost);
                    book.setId(id);
                    books.add(book);
                }

                return books;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    public static List<Book> getBorrowedBooks() {
        List<Book> books = new ArrayList();

        try {
            String query = "SELECT b.*, a.id AS author_id, a.name AS author_name\nFROM books AS b\nJOIN authors AS a ON b.author_id = a.id WHERE b.borrowed > 0";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int authorId = resultSet.getInt("author_id");
                String authorName = resultSet.getString("author_name");
                String isbn = resultSet.getString("isbn");
                int available = resultSet.getInt("available");
                int borrowed = resultSet.getInt("borrowed");
                int lost = resultSet.getInt("lost");
                Author author = new Author(authorId, authorName);
                Book book = new Book(title, author, isbn, available, borrowed, lost);
                book.setId(id);
                books.add(book);
            }

            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
public static List<Book> getLostBooks() {
        List<Book> books = new ArrayList();

        try {
            String query = "SELECT b.*, a.id AS author_id, a.name AS author_name\nFROM books AS b\nJOIN authors AS a ON b.author_id = a.id WHERE b.lost > 0";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int authorId = resultSet.getInt("author_id"); // Fetch author ID from the database
                String authorName = resultSet.getString("author_name");
                String isbn = resultSet.getString("isbn");
                int available = resultSet.getInt("available");
                int borrowed = resultSet.getInt("borrowed");
                int lost = resultSet.getInt("lost");
                Author author = new Author(authorId, authorName); // Create Author with ID and Name
                Book book = new Book(title, author, isbn, available, borrowed, lost);
                book.setId(id);
                books.add(book);
            }

            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public Author getAuthor() {
        return this.author;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public int getAvailable() {
        return this.available;
    }

    public int getBorrowed() {
        return this.borrowed;
    }

    public int getLost() {
        return this.lost;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setBorrowed(int borrowed) {
        this.borrowed = borrowed;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public static void create(String title, Author author, String isbn, int available) {
        try {
            String insertQuery = "INSERT INTO books (title, author_id, isbn, available) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, author.getId());
            preparedStatement.setString(3, isbn);
            preparedStatement.setInt(4, available);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book added successfully.");
            } else {
                System.out.println("Failed to add the book.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while adding the book.");
        }
        Book newBook = new Book(title, author, isbn, available, 0, 0);
        books.add(newBook);
    }

    public static Book read(int id) {
        try {
            String query = "SELECT b.*, a.id AS author_id, a.name AS author_name\nFROM books AS b\nJOIN authors AS a ON b.author_id = a.id WHERE b.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                int authorId = resultSet.getInt("author_id");
                String authorName = resultSet.getString("author_name");
                String isbn = resultSet.getString("isbn");
                int available = resultSet.getInt("available");
                int borrowed = resultSet.getInt("borrowed");
                int lost = resultSet.getInt("lost");
                Author author = new Author(authorId, authorName);
                Book book = new Book(title, author, isbn, available, borrowed, lost);
                book.setId(id);
                return book;
            } else {
                return null; // Book with the specified ID not found
            }
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while reading the book.", e);
        }
    }

    public static void update(int id) {
        Scanner scanner = new Scanner(System.in);

        try {
            String selectQuery = "SELECT * FROM books WHERE id = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setInt(1, id);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                String currentTitle = resultSet.getString("title");
                String currentIsbn = resultSet.getString("isbn");
                int currentAvailable = resultSet.getInt("available");
                System.out.print("Enter new title (press Enter to keep previous value '" + currentTitle + "'): ");
                String newTitle = scanner.nextLine();
                if (newTitle.isEmpty()) {
                    newTitle = currentTitle;
                }

                System.out.print("Enter new ISBN (press Enter to keep previous value '" + currentIsbn + "'): ");
                String newIsbn = scanner.nextLine();
                if (newIsbn.isEmpty()) {
                    newIsbn = currentIsbn;
                }

                System.out.print("Enter new copies available (press Enter to keep previous value '" + currentAvailable + "'): ");
                String availableInput = scanner.nextLine();
                int newAvailable;
                if (availableInput.isEmpty()) {
                    newAvailable = currentAvailable;
                } else {
                    newAvailable = Integer.parseInt(availableInput);
                }

                String updateQuery = "UPDATE books SET title = ?, isbn = ?, available = ? WHERE id = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, newTitle);
                updateStatement.setString(2, newIsbn);
                updateStatement.setInt(3, newAvailable);
                updateStatement.setInt(4, id);
                int rowsAffected = updateStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Book updated successfully.");
                } else {
                    System.out.println("Book selected not found.");
                }

                updateStatement.close();
            } else {
                System.out.println("Book with selected not found.");
            }

            resultSet.close();
            selectStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while updating the book.");
        }
    }

    public static void delete(int id) {
        try {
            String deleteQuery = "DELETE FROM books WHERE id = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setInt(1, id);
            int rowsAffected = deleteStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book deleted successfully.");
            } else {
                System.out.println("Book selected not found.");
            }

            deleteStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while deleting the book.");
        }
    }
    public static void borrow(Date returnDate, Book book, Member member, String borrowingDate) {
        try {
            String insertQuery = "INSERT INTO borrowings (book_id, member_id, borrowing_date, return_date) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, book.getId());
            preparedStatement.setInt(2, member.getId());
            preparedStatement.setDate(3, Date.valueOf(borrowingDate));
            preparedStatement.setDate(4, returnDate);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book borrowed successfully.");
            } else {
                System.out.println("Failed to borrow the book.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while borrowing the book.");
        }
    }
    public static void returning(Member member, Book book) {
        try {
            String insertQuery = "DELETE FROM borrowings Where member_id=? AND book_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, member.getId());
            preparedStatement.setInt(2, book.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book returned successfully.");
            } else {
                System.out.println("Failed to return the book.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while returning the book.");
        }
    }

}
