package com.security.demo;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.*;

public class UserRepositoryTest {
    private ConnectionFactory connectionFactory = new ConnectionFactory();
    private Connection conn;

    @Before
    public void setup() throws Exception {
        conn = connectionFactory.createInMemoryDatabase();
    }

    @Test
    public void should_create_user_with_given_username_password() throws Exception {
        UserRepository repo = new UserRepository(conn);
        repo.createUser("Mukund", "password");

        assertEquals(repo.getUserCount(), 1);
    }

    @Test
    public void should_return_true_if_logged_in_successfully() throws Exception {
        UserRepository repo = new UserRepository(conn);
        repo.createUser("Aakash", "password");
        repo.createUser("Baghya", "password");

        Boolean loggedIn = repo.loginUser("Aakash", "password");
        assertTrue(loggedIn);
    }

    @Test
    public void should_return_only_users_with_interest_in_science() throws Exception {
        UserRepository repo = new UserRepository(conn);
        repo.createUser("Aakash", "password", "science");
        repo.createUser("Baghya", "password", "economics");
        repo.createUser("Baghya", "password", "economics");

        List<User> usersInterestedInScience = repo.getUsersInterestedIn("science");

        assertEquals(1, usersInterestedInScience.size());
    }

    @Test
    public void should_return_false_if_user_is_not_present() throws Exception {
        UserRepository repo = new UserRepository(conn);
        repo.createUser("Aakash", "password");
        repo.createUser("Baghya", "password");

        Boolean loggedIn = repo.loginUser("Attacker", "' or 1=1 --comment");

        assertFalse(loggedIn);
    }

    @Test
    public void should_list_count_of_all_users() throws Exception {
        UserRepository repo = new UserRepository(conn);
        repo.createUser("Aakash", "password", "science");
        repo.createUser("Baghya", "password", "economics");
        repo.createUser("Cheeri", "password", "economics");
        assertEquals(3, repo.getUserCount());

        List<User> usersInterestedInScience = repo.getUsersInterestedIn("science' UNION select * from users --comment");

        assertEquals(0, usersInterestedInScience.size());
    }
}