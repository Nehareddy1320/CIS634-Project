package project.foodwastemanagement.database;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Location {
    @Id
    public long id;
    public String state;
    public String county;
    public String town;
}