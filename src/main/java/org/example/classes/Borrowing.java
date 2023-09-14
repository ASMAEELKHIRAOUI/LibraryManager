package org.example.classes;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Borrowing {
    private static Connection connection = DBConnection.createDBConnection();
    private int id;
    private Book book;
    private Member member;
    private String borrowDate;
    private String returnDate;
    private boolean lost;
    private boolean returned;
    private static List<Borrowing> borrowings = new ArrayList();

    public Borrowing(int id, Book book, Member member, String borrowDate, String returnDate, boolean lost, boolean returned) {
        this.id = id;
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.lost = false;
        this.returned = false;
    }

    public static <Int> Borrowing read(int memberId, int bookId) {
        try {
            String query = "SELECT * FROM borrowings WHERE member_id = ? AND book_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, memberId);
            preparedStatement.setInt(2, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int bookID = resultSet.getInt("book_id");
                int memberID = resultSet.getInt("member_id");
                String borrowingDate = resultSet.getString("borrowing_date");
                String returnDate = resultSet.getString("return_date");
                Boolean returned = resultSet.getBoolean("returned");
                Boolean lost = resultSet.getBoolean("lost");
                Book bookObject = Book.read(bookID);
                Member memberObject = Member.read(memberID);
                Borrowing borrowing = new Borrowing(id, bookObject, memberObject, borrowingDate, returnDate, returned, lost);
                borrowing.setId(id);
                return borrowing;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Borrowing> getAllBorrowings() {
        List<Book> books = new ArrayList();

        try {
            String query = "SELECT * FROM borrowings WHERE lost = 0;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int bookId = resultSet.getInt("book_id");
                int memberId = resultSet.getInt("member_id");
                String borrowingDate = resultSet.getString("borrowing_date");
                String returnDate = resultSet.getString("return_date");
                Boolean returned = resultSet.getBoolean("returned");
                Boolean lost = resultSet.getBoolean("lost");
                Book bookObject = Book.read(bookId);
                Member memberObject = Member.read(memberId);
                Borrowing borrowing = new Borrowing(id, bookObject, memberObject, borrowingDate, returnDate, returned, lost);
                borrowing.setId(id);
                borrowings.add(borrowing);
            }

            return borrowings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getId() {
        return this.id;
    }

    public Book getBook() {
        return this.book;
    }

    public Member getMember() {
        return this.member;
    }

    public String getBorrowDate() {
        return this.borrowDate;
    }

    public String getReturnDate() {
        return this.returnDate;
    }

    public boolean getLost() {
        return this.lost;
    }

    public boolean getReturned() {
        return this.returned;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public void borrowBook(Book book, Member member, String borrowDate, String returnDate) {
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public void returnBook(boolean returned) {
        this.returned = true;
    }

    public void update(Book book, Member member, String borrowDate, String returnDate) {
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }
    public static void updateBorrowing(Borrowing borrowing) {
        try {
            String query = "UPDATE borrowings SET lost = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, borrowing.getLost());
            preparedStatement.setInt(2, borrowing.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while updating the borrowing.");
        }
    }
}
