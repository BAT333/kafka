package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDataBase {

    private final Connection connectio;

    public ServiceDataBase(Connection connectio) {
        this.connectio = connectio;
    }

    public User save(User user)  {
        String save = "insert into usuario(login,password) value(?,?)";
        PreparedStatement statement = null;
        try {
            statement = connectio.prepareStatement(save);
            connectio.setAutoCommit(false);
            statement.setString(1,user.getLogin());
            statement.setString(2,user.getPasswords());
            statement.execute();
            connectio.commit();
            statement.close();
            connectio.close();
        } catch (SQLException e) {
            try {
                connectio.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }


        return user;

    }

    public boolean existsUser(String email) throws SQLException {
        String query ="select id from usuario " +
                "where login = ? limit 1";
        var statement = this.connectio.prepareStatement(query);
        statement.setString(1,email);
        var results = statement.executeQuery();
        return !results.next();
    }

    public List<User> all() {
        String query ="select id from usuario ";
        List<User> users = new ArrayList<>();
        try {
            var statement = this.connectio.prepareStatement(query);
            var resulte =  statement.executeQuery();
            while (resulte.next()){
                users.add(new User(String.valueOf(resulte.getInt(1))));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }
}
