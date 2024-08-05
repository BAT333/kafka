package org.example;

public class Main {

    public static void main(String[] args) {
        UserService service = new UserService();
       var user =  service.save(new User("rafael@gmail.com","senha"));
        System.out.println(user);
    }
}