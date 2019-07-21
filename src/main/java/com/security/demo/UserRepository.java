package com.security.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public void createUser(String username, String password) throws Exception {
        String query = "insert into users values (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setString(3, "AKIAIOSFODNN7EXAMPLE");
        stmt.execute();
    }

    public void createUser(String username, String password, String areaOfInterest) throws Exception {
        String query = "insert into users values (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setString(3, areaOfInterest);
        stmt.execute();
    }

    public int getUserCount() throws Exception {
        String userCountQuery = "select count(*) as user_count from users";
        ResultSet resultSet = connection.createStatement().executeQuery(userCountQuery);

        resultSet.next();

        return resultSet.getInt("user_count");
    }

    public List<User> getUsersInterestedIn(String areaOfInterest) throws Exception {
        String query = "select * from users where area_of_interest = '" + areaOfInterest + "'";
        ResultSet resultSet = connection.createStatement().executeQuery(query);
        List<User> userList = new ArrayList<User>();

        while(resultSet.next()) {
            userList.add(new User(resultSet.getString("username"), areaOfInterest));
        }
        return userList;
    }

    public Boolean loginUser(String userName, String password) throws SQLException {
        String query = "select * from users where username = '" + userName + "' and password = '" + password + "'";

        ResultSet resultSet = connection.createStatement().executeQuery(query);

        if (resultSet.next()) {
            return true;
        } else {
            return false;
        }
    }
}
