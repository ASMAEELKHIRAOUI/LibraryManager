package library;
import java.util.List;

public class Book {

    private int id;
    private String title;
    private Author author;
    private String isbn;
    private int available;
    private int borrowed;
    private int lost;
    private List<Book> books;

    // Constructor with id
    public Book(int id, String title, Author author, String isbn, int available, int borrowed, int lost) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = available;
        this.borrowed = borrowed;
        this.lost = lost;
    }

    // Getter methods
    public List<Book> getAllBooks() {
        return books;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getAvailable() {
        return available;
    }

    public int getBorrowed() {
        return borrowed;
    }

    public int getLost() {
        return lost;
    }

    // Setter methods
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

    public void create(String title, Author author, String isbn, int available){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = available;
    }

    public static Book show(int id){
        return null;
    }
    
    public void update(String title, Author author, String isbn, int available){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = available;
    }

    public void delete(){

    }

}
