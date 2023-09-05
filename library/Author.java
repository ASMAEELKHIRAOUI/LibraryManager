package library;
public class Author {
    private int id;
    private String name;

    // Constructor
    public Author(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void create(String name){
        this.name = name;
    }

    public void read(){

    }

    public static Author show(int id){
        return null;
    }

    public void update(String name){
        this.name = name;
    }

    public void delete(){

    }

}