package project.foodwastemanagement.database;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class FoodWaste {
    @Id
    public long id;
    public long user_id;
    public long location_id;
    public String name;
    public String status;
    public String status_message;
}