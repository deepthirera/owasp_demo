package com.security.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public void createUser(String username, String password) throws Exception {
        final String query = "insert into users values (?, ?, ?)";
        final PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setString(3, "maths");
        stmt.execute();
    }

    public void createUser(String username, String password, String areaOfInterest) throws Exception {
        final String query = "insert into users values (?, ?, ?)";
        final PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setString(3, areaOfInterest);
        stmt.execute();
    }

    public int getUserCount() throws Exception {
        final String userCountQuery = "select count(*) as user_count from users";
        final ResultSet resultSet = connection.createStatement().executeQuery(userCountQuery);

        resultSet.next();

        return resultSet.getInt("user_count");
    }

    public List<User> getUsersInterestedIn(String areaOfInterest) throws Exception {
        final String query = "select * from users where area_of_interest = '" + areaOfInterest + "'";
        final ResultSet resultSet = connection.createStatement().executeQuery(query);
        List<User> userList = new ArrayList<User>();

        while(resultSet.next()) {
            userList.add(new User(resultSet.getString("username"), areaOfInterest));
        }
        return userList;
    }

    public Boolean loginUser(String userName, String password) throws SQLException {
        final String query = "select * from users where username = '" + userName + "' and password = '" + password + "'";

        final ResultSet resultSet = connection.createStatement().executeQuery(query);

        if (resultSet.next()) {
            return true;
        } else {
            return false;
        }
    }
}
