package library;
public class Member{
    private int id;
    private String name;
    private String membership;

    // Constructor
    public Member(int id, String name, String membership) {
        this.id = id;
        this.name = name;
        this.membership = membership;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMembership() {
        return membership;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public void create(String name, String membership){

    }

    public void read(){

    }

    public static Member show(int id){
        return null;
    }

    public void update(String name, String membership){

    }

    public void delete(){

    }

}