package org.example;

import java.util.UUID;

public class User {
    private final String uuid;

    public User(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                '}';
    }

    public String getUuid() {
        return uuid;
    }

    public String getTaget() {
        return "target/"+uuid +"-report.txt";
    }
}
