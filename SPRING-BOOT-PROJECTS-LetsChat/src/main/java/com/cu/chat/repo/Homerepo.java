package com.cu.chat.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cu.chat.model.userdetails;

@Repository
public class Homerepo {

    private static final String URL = "jdbc:mysql://localhost:3306/letschat";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    public List<userdetails> getuserdetails(int id) {
        List<userdetails> users = new ArrayList<>();

        String query = "SELECT * FROM user WHERE id = ?";
        
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
            	userdetails user = new userdetails();
            	user.setId(resultSet.getLong("id"));
                user.setFullName(resultSet.getNString("full_name"));
                user.setEmail(resultSet.getString("email"));
                user.setCity(resultSet.getString("city"));
                user.setPassword(resultSet.getString("password"));
               // user.setEmail(resultSet.getString("email"));
                
                users.add(user);
                
               
            }
            System.out.println("USER DETAILS"+users);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
}
