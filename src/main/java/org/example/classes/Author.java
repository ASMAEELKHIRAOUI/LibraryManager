package org.example.classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Author {
    private static Connection connection = DBConnection.createDBConnection();
    private int id;
    private String name;
    private static List<Author> authors = new ArrayList();


    public Author(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Author> read() {
        String query = "SELECT * FROM authors;";

        try {
            Connection connection = DBConnection.createDBConnection();
            PreparedStatement p = connection.prepareStatement(query);
            ResultSet r = p.executeQuery();

            while (r.next()) {
                int id = r.getInt("id");
                String name = r.getString("name");
                Author author = new Author(id, name);
                authors.add(author);
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return authors;
    }
public static Author show(int id) {
        Iterator authorsList = authors.iterator();

        Author author;
        do {
            if (!authorsList.hasNext()) {
                return null;
            }

            author = (Author)authorsList.next();
        } while(author.getId() != id);

        return author;
    }

    public static Author getAuthorById(int id) {
        Iterator authorsList = authors.iterator();

        Author author;
        do {
            if (!authorsList.hasNext()) {
                return null;
            }

            author = (Author)authorsList.next();
        } while(author.getId() != id);

        return author;
    }

    public static Author create(String name) {
        try {
            String insertQuery = "INSERT INTO authors (name) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Author added successfully.");
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    return new Author(id, name);
                }
            }
            System.out.println("Failed to add the author.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while adding the author.");
        }
        return null;
    }

}
