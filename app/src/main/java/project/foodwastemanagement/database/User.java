package project.foodwastemanagement.database;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class User {
    @Id
    public long id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public boolean admin;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                '}';
    }
}