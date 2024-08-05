package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConneectFactory {
    
    public Connection connection (){
        try {
            return this.hakari().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private HikariDataSource hakari() {
        var config =new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost/bancoLouco?createDatabaseIfNotExist=true&serverTimezone=America/Sao_Paulo&useSSl=false");
        config.setUsername("root");
        config.setPassword("bp1234");
        config.setMaximumPoolSize(10);
        config.setMaxLifetime(3000);

        return new HikariDataSource(config);
    }
}
