package org.example.classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Member {
    private static Connection connection = DBConnection.createDBConnection();
    private int id;
    private String name;
    private String membership;
    private static List<Member> members = new ArrayList();

    public Member(int id, String name, String membership) {
        this.id = id;
        this.name = name;
        this.membership = membership;
    }

    public Member() {
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getMembership() {
        return this.membership;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public static List<Member> read() {
        List<Member> members = new ArrayList();

        try {
            String query = "SELECT * FROM members;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String membership = resultSet.getString("membership");
                Member member = new Member(id, name, membership);
                member.setId(id);
                members.add(member);
            }

            return members;
        } catch (SQLException var8) {
            throw new RuntimeException(var8);
        }
    }

    public static Member read(int id) {
        try {
            String query = "SELECT * FROM members WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String membership = resultSet.getString("membership");
                Member member = new Member(id, name, membership);
                member.setId(id);
                return member;
            } else {
                return null; // Member with the specified ID not found
            }
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while reading the member.", e);
        }
    }

    public static Member create(String name, String membership) {
        try {
            String insertQuery = "INSERT INTO members (name, membership) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, membership);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Member added successfully.");
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    return new Member(id, name, membership);
                }
            }
            System.out.println("Failed to add the member.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while adding the member.");
        }
        return null;
    }

}
