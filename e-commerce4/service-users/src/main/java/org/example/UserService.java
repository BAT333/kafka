package org.example;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    private ConneectFactory factory = new ConneectFactory();



    public User save(User user){
        ServiceDataBase serviceDataBase = new ServiceDataBase(factory.connection());
        return serviceDataBase.save(user);
    }

    public boolean exists(String email) throws SQLException {
        ServiceDataBase serviceDataBase = new ServiceDataBase(factory.connection());
        return serviceDataBase.existsUser(email);
    }

    public List<User> allUser() {
        ServiceDataBase serviceDataBase = new ServiceDataBase(factory.connection());
        return serviceDataBase.all();
    }
}
