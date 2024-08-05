package org.example;


import java.util.List;
import java.util.Objects;


public class User {



    private String login;

    private String passwords;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public User(String login, String passwords) {
        this.login = login;
        this.passwords = passwords;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", passwords='" + passwords + '\'' +
                '}';
    }
}
