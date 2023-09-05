package library;

import java.util.Date;

public class Borrowing {
    private int id;
    private Book book;
    private Member member;
    private Date borrowDate;
    private Date returnDate;
    private boolean lost;
    private boolean returned;

    // Constructor
    public Borrowing(int id, Book book, Member member, Date borrowDate, Date returnDate, boolean lost, boolean returned) {
        this.id = id;
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.lost = false;
        this.returned = false;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public boolean getLost() {
        return lost;
    }

    public boolean getReturned() {
        return returned;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public void borrowBook(Book book, Member member, Date borrowDate, Date returnDate) {
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public void returnBook(boolean returned) {
        this.returned = true;
    }

    public void update(Book book, Member member, Date borrowDate, Date returnDate) {
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }
}
