package ch.bfh.ti.advancedweb.voting.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class User {

    @Id
    private String id;

    private String username;

    private String password;

    public User(String username, String password) {
        this.id = UUID.randomUUID().toString();
        this.password = password;
        this.username = username;
    }

    protected User() {
        // FOR JPA
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }
}
